package com.vili.mrmentoria.engine.request;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.servlet.HandlerMapping;

import com.vili.mrmentoria.engine.IEntity;
import com.vili.mrmentoria.engine.exceptions.FieldMessage;
import com.vili.mrmentoria.engine.exceptions.custom.InvalidRequestParamException;
import com.vili.mrmentoria.engine.request.annotations.FilterSetting;
import com.vili.mrmentoria.engine.request.annotations.NoFilter;
import com.vili.mrmentoria.engine.utils.CustomCaster;
import com.vili.mrmentoria.engine.utils.URL;

public class Request {

	public static final int DEFAULT_PAGE = 0;
	public static final int DEFAULT_SIZE = 10;
	public static final String[] DEFAULT_ORDER_BY = { "id" };
	public static final Direction DEFAULT_DIRECTION = Direction.ASC;

	public static final String PAGE_ATTRIBUTE = "page";
	public static final String DIRECTION_PARAM = "direction";
	public static final String ORDER_BY_PARAM = "orderBy";
	public static final String SIZE_PARAM = "size";
	public static final String FIELDS_PARAM = "fields";

	public static final Map<String, Class<?>> PAGE_REQUEST_PARAMS = Map.of(DIRECTION_PARAM, String.class,
			ORDER_BY_PARAM, String.class, SIZE_PARAM, Integer.class);

	private Class<?> entityClass;
	private HttpServletRequest payload;
	private Pageable pageRequest;
	private Set<String> fields = new HashSet<>();
	private Set<Filter> filters = new HashSet<>();
	private List<FieldMessage> errors = new ArrayList<>();

	public Request(HttpServletRequest request, Class<?> entityClass) {
		setEntityClass(entityClass);
		setPayload(request);;
		setPageRequest();
		setFields();
		setFilters();
	}

	public Request(Pageable pageRequest) {
		this.pageRequest = pageRequest;
	}

	public HttpServletRequest getPayload() {
		return payload;
	}

	private void setPayload(HttpServletRequest payload) {
		this.payload = payload;
	}

	public Pageable getPageRequest() {
		return pageRequest;
	}

	@SuppressWarnings("unchecked")
	private void setPageRequest() {
		if (getPayload() == null)
			return;

		int page = DEFAULT_PAGE;
		Map<String, String> pathVariables = (Map<String, String>) getPayload()
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

		for (Entry<String, String> entry : pathVariables.entrySet()) {
			String key = entry.getKey();

			if (key.equals(PAGE_ATTRIBUTE)) {
				try {
					page = Integer.parseInt(entry.getValue()) - 1;
					
					if (page < 0) 
						throw new InvalidRequestParamException(Arrays.asList(new FieldMessage(key, "Path variable must be a positive whole number")));
				} catch (NumberFormatException e) {
					throw new InvalidRequestParamException(Arrays.asList(new FieldMessage(key, "Path variable must be a positive whole number")));
				}
			}
		}

		Map<String, Object> parameterMap = new HashMap<>();

		for (Entry<String, String[]> entry : getPayload().getParameterMap().entrySet()) {
			String key = entry.getKey();

			if (PAGE_REQUEST_PARAMS.keySet().contains(key)) {
				String paramValue = entry.getValue()[0];

				if (paramValue.isBlank()) {
					throw new InvalidRequestParamException(Arrays.asList(new FieldMessage(key, "Request parameter must not be null. Inform a valid value or remove from request")));
				}

				Object value = CustomCaster.castTo(PAGE_REQUEST_PARAMS.get(key), paramValue);

				if (value != null)
					parameterMap.putIfAbsent(key, value);
			}
		}

		int size = parameterMap.get(SIZE_PARAM) == null ? DEFAULT_SIZE : (int) parameterMap.get(SIZE_PARAM);
		String[] orderBy = parameterMap.get(ORDER_BY_PARAM) == null ? DEFAULT_ORDER_BY
				: ((String) parameterMap.get(ORDER_BY_PARAM)).split(",");
		Direction direction = parameterMap.get(DIRECTION_PARAM) == null ? DEFAULT_DIRECTION
				: Direction.valueOf(((String) parameterMap.get(DIRECTION_PARAM)).toUpperCase());

		pageRequest = PageRequest.of(page, size, direction, orderBy);
	}

	public Set<String> getFields() {
		return fields;
	}

	private void setFields() {
		if (getPayload() == null)
			return;

		Map<String, Object> parameterMap = new HashMap<>();

		for (Entry<String, String[]> entry : getPayload().getParameterMap().entrySet()) {
			String key = entry.getKey();

			if (key.equals(FIELDS_PARAM)) {
				String paramValue = entry.getValue()[0];

				if (paramValue.isBlank()) {
					throw new InvalidRequestParamException(Arrays.asList(new FieldMessage(key, "Request parameter must not be null. Inform a valid value or remove from request")));
				}

				Object value = CustomCaster.castTo(String.class, paramValue);

				if (value != null)
					parameterMap.putIfAbsent(key, value);
			}
		}

		if (parameterMap.get(FIELDS_PARAM) != null) {
			fields = Set.copyOf(Arrays.asList(((String) parameterMap.get(FIELDS_PARAM)).split(",")));
		}
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	private void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}
	
	private Set<Filter> getFilters() {
		return filters;
	}

	private void setFilters() {
		for (Field field : getEntityClass().getDeclaredFields()) {
			if (!field.isAnnotationPresent(NoFilter.class)) {
				FilterSetting[] filterSettings = field.getDeclaredAnnotationsByType(FilterSetting.class);
				
				if (filterSettings.length > 0) {
					for (FilterSetting fs : filterSettings) {
						String alias = fs.alias().isBlank() ? field.getName() : fs.alias();
						buildFilter(field, alias, fs.nesting());
					}
				} else {
					buildFilter(field, field.getName(), new String[0]);
				}
			}
		}
		
		if (errors.size() > 0) 
			throw new InvalidRequestParamException(errors);

	}
	
	public <T extends IEntity> Specification<T> getSpecification() {
		return SpecificationBuilder.with(filters).build();
	}

	@SuppressWarnings("unchecked")
	private void buildFilter(Field field, String alias, String[] nesting) {
		String[] params = getPayload().getParameterMap().get(alias);

		if (params == null)
			return;

		String fieldName = field.getName() + resolveNestedPath(nesting);
		Class<?> fieldType = resolveFieldType(field);
		
		if (nesting.length > 0)
			fieldType = resolveNestedType(fieldType, nesting[nesting.length - 1]);

		for (String param : params) {
			if (param.isBlank()) {
				errors.add(new FieldMessage(fieldName, "Request parameter must not be null. Inform a valid value or remove from request"));
				continue;
			}
			
			String decodedParam = fieldType.equals(String.class) ? URL.decodeParam(param) : param;
			QueryOperator qo = QueryOperator.from(decodedParam);
			Filter filter = Filter.builder().fieldName(fieldName).fieldType(fieldType).operator(qo);

			if (qo.equals(QueryOperator.IN) || qo.equals(QueryOperator.BETWEEN))
				filter.values((List<String>) QueryOperator.treat(param));
			else
				filter.value((String) QueryOperator.treat(param));
			
			getFilters().add(filter);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void buildFilter(Field field, String[] params, String[] nesting) {
		if (params == null)
			return;

		String fieldName = field.getName() + resolveNestedPath(nesting);
		Class<?> fieldType = resolveFieldType(field);
		
		if (nesting.length > 0)
			fieldType = resolveNestedType(fieldType, nesting[nesting.length - 1]);

		for (String param : params) {
			if (param.isBlank()) {
				errors.add(new FieldMessage(fieldName, "Request parameter must not be null. Inform a valid value or remove from request"));
				continue;
			}
			
			String decodedParam = fieldType.equals(String.class) ? URL.decodeParam(param) : param;
			QueryOperator qo = QueryOperator.from(decodedParam);
			Filter filter = Filter.builder().fieldName(fieldName).fieldType(fieldType).operator(qo);

			if (qo.equals(QueryOperator.IN) || qo.equals(QueryOperator.BETWEEN))
				filter.values((List<String>) QueryOperator.treat(param));
			else
				filter.value((String) QueryOperator.treat(param));
			
			getFilters().add(filter);
		}
	}

	private Class<?> resolveFieldType(Field field) {
		Class<?> fieldType = field.getType();
		
		if (Collection.class.isAssignableFrom(fieldType)) {
			fieldType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
			
			if (!IEntity.class.isAssignableFrom(fieldType)) {
				fieldType = field.getType();
			}
		}
		
		return fieldType;
	}
	
	private Class<?> resolveNestedType(Class<?> type, String fieldName) {
		if (type == null)
			return type;

		for (Field field : type.getDeclaredFields()) {
			if (field.getName().equals(fieldName)) {
				return resolveFieldType(field);
			}
		}

		return resolveNestedType(type.getSuperclass(), fieldName);
	}
	
	private String resolveNestedPath(String[] nesting) {
		String nestedPath = Arrays.asList(nesting).stream().collect(Collectors.joining("."));
		return nestedPath.isBlank() ? nestedPath : "." + nestedPath;
	}

	public static Pageable paging() {
		return PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE, DEFAULT_DIRECTION, DEFAULT_ORDER_BY);
	}
}
