package com.vili.mrmentoria.engine.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.vili.mrmentoria.engine.IEntity;
import com.vili.mrmentoria.engine.exceptions.custom.OperationNotSupportedException;
import com.vili.mrmentoria.engine.utils.CustomCaster;

public class SpecificationBuilder {

	private Map<String, List<Filter>> filterMapping = new HashMap<>();
	
	private SpecificationBuilder(Collection<Filter> filters) {
		setFilterMapping(filters);
	}

	public Map<String, List<Filter>> getFilterMapping() {
		return filterMapping;
	}

	public void setFilterMapping(Collection<Filter> filters) {	
		for (Filter filter : filters.stream().collect(Collectors.toList())) {
			String name = filter.fieldName();
			
			if (filterMapping.containsKey(name)) {
				List<Filter> list = new ArrayList<>();
				filterMapping.get(name).forEach(x -> list.add(x));
				list.add(filter);
				filterMapping.replace(name, list);
			}
			else
				filterMapping.put(name, Arrays.asList(filter));
		}
	}

	public static SpecificationBuilder with(Collection<Filter> filters) {
		return new SpecificationBuilder(filters);
	}

	public <T extends IEntity> Specification<T> build() {
		if (getFilterMapping().size() == 0)
			return null;
		
		Specification<T> specification = Specification.where(null);
		
		for (Entry<String, List<Filter>> entry : getFilterMapping().entrySet()) {
			List<Filter> filters = entry.getValue();

			if (filters.size() > 1)
				specification = or(specification, filters);
			else
				specification = specification.and(createSpecification(filters.get(0)));
		}

		return specification;
	}
	
	private <T extends IEntity> Specification<T> or(Specification<T> specification, List<Filter> filters) {
		Specification<T> orSpec = createSpecification(filters.get(0));

		for (Filter filter : filters.subList(1, filters.size())) {
			orSpec = orSpec.or(createSpecification(filter));
		}
		
		return specification.and(orSpec);
	}

	@SuppressWarnings("unchecked")
	private <T extends IEntity> Specification<T> createSpecification(Filter filter) {
		switch (filter.operator()) {

		case EQUALS:
			return (root, query, cb) -> {
				String[] path = filter.fieldName().split("\\.");
				Object y = CustomCaster.castTo(filter.fieldType(), filter.value());
				int len = path.length;

				if (len > 1) {
					Join<Object, Object> join = root.join(path[0]);

					if (len > 1) {
						for (int i = 1; i < len - 1; i++) {
							join = join.join(path[i]);
						}
					}

					if (Collection.class.isAssignableFrom(filter.fieldType())) {
						Object x = ((Collection<Object>) y).stream().collect(Collectors.toList()).get(0);
						return cb.equal(join.join(path[len - 1]), x);
					}
					else 
						return cb.equal(join.get(path[len - 1]), y);
				}

				if (Collection.class.isAssignableFrom(filter.fieldType())) {
					Object x = ((Collection<Object>) y).stream().collect(Collectors.toList()).get(0);
					return cb.equal(root.join(path[len - 1]), x);
				}
				else 
					return cb.equal(root.get(path[len - 1]), y);
			};

		case NOT_EQUALS:
			return (root, query, cb) -> {
				String[] path = filter.fieldName().split("\\.");
				Object y = CustomCaster.castTo(filter.fieldType(), filter.value());
				int len = path.length;

				if (len > 1) {
					Join<Object, Object> join = root.join(path[0]);

					if (len > 1) {
						for (int i = 1; i < len - 1; i++) {
							join = join.join(path[i]);
						}
					}

					if (Collection.class.isAssignableFrom(filter.fieldType())) {
						Object x = ((Collection<Object>) y).stream().collect(Collectors.toList()).get(0);
						return cb.notEqual(join.join(path[len - 1]), x);
					}
					else 
						return cb.notEqual(join.get(path[len - 1]), y);
				}

				if (Collection.class.isAssignableFrom(filter.fieldType())) {
					Object x = ((Collection<Object>) y).stream().collect(Collectors.toList()).get(0);
					return cb.notEqual(root.join(path[len - 1]), x);
				}
				else 
					return cb.notEqual(root.get(path[len - 1]), y);
			};

		case GREATER_THAN:
			return (root, query, cb) -> {
				String[] path = filter.fieldName().split("\\.");
				Object y = CustomCaster.castTo(filter.fieldType(), filter.value());
				int len = path.length;

				if (len > 1) {
					Join<Object, Object> join = root.join(path[0]);

					if (len > 1) {
						for (int i = 1; i < len - 1; i++) {
							join = join.join(path[i]);
						}
					}

					if (Collection.class.isAssignableFrom(filter.fieldType())) {
						Object x = ((Collection<Object>) y).stream().collect(Collectors.toList()).get(0);
						
						if (x.getClass().isAssignableFrom(Date.class)) 
							return cb.greaterThan(join.join(path[len - 1]), (Date) x);
						else 
							return cb.gt(join.join(path[len - 1]), (Number) x);
					}
					else {
						if (filter.fieldType().isAssignableFrom(Date.class)) 
							return cb.greaterThan(join.get(path[len - 1]), (Date) y);
						else 
							return cb.gt(join.get(path[len - 1]), (Number) y);
					}
				}

				if (Collection.class.isAssignableFrom(filter.fieldType())) {
					Object x = ((Collection<Object>) y).stream().collect(Collectors.toList()).get(0);
					
					if (x.getClass().isAssignableFrom(Date.class)) 
						return cb.greaterThan(root.join(path[len - 1]), (Date) x);
					else 
						return cb.gt(root.join(path[len - 1]), (Number) x);
				}
				else {
					if (filter.fieldType().isAssignableFrom(Date.class)) 
						return cb.greaterThan(root.get(path[len - 1]), (Date) y);
					else 
						return cb.gt(root.get(path[len - 1]), (Number) y);
				}
			};

		case LESS_THAN:
			return (root, query, cb) -> {
				String[] path = filter.fieldName().split("\\.");
				Object y = CustomCaster.castTo(filter.fieldType(), filter.value());
				int len = path.length;

				if (len > 1) {
					Join<Object, Object> join = root.join(path[0]);

					if (len > 1) {
						for (int i = 1; i < len - 1; i++) {
							join = join.join(path[i]);
						}
					}

					if (Collection.class.isAssignableFrom(filter.fieldType())) {
						Object x = ((Collection<Object>) y).stream().collect(Collectors.toList()).get(0);
						
						if (x.getClass().isAssignableFrom(Date.class)) 
							return cb.lessThan(join.join(path[len - 1]), (Date) x);
						else 
							return cb.lt(join.join(path[len - 1]), (Number) x);
					}
					else {
						if (filter.fieldType().isAssignableFrom(Date.class)) 
							return cb.lessThan(join.get(path[len - 1]), (Date) y);
						else 
							return cb.lt(join.get(path[len - 1]), (Number) y);
					}
				}

				if (Collection.class.isAssignableFrom(filter.fieldType())) {
					Object x = ((Collection<Object>) y).stream().collect(Collectors.toList()).get(0);
					
					if (x.getClass().isAssignableFrom(Date.class)) 
						return cb.lessThan(root.join(path[len - 1]), (Date) x);
					else 
						return cb.lt(root.join(path[len - 1]), (Number) x);
				}
				else {
					if (filter.fieldType().isAssignableFrom(Date.class)) 
						return cb.lessThan(root.get(path[len - 1]), (Date) y);
					else 
						return cb.lt(root.get(path[len - 1]), (Number) y);
				}
			};

		case GREATER_OR_EQUAL:
			return (root, query, cb) -> {
				String[] path = filter.fieldName().split("\\.");
				Object y = CustomCaster.castTo(filter.fieldType(), filter.value());
				int len = path.length;

				if (len > 1) {
					Join<Object, Object> join = root.join(path[0]);

					if (len > 1) {
						for (int i = 1; i < len - 1; i++) {
							join = join.join(path[i]);
						}
					}
					
					if (Collection.class.isAssignableFrom(filter.fieldType())) {
						Object x = ((Collection<Object>) y).stream().collect(Collectors.toList()).get(0);
						
						if (x.getClass().isAssignableFrom(Date.class)) 
							return cb.greaterThanOrEqualTo(join.join(path[len - 1]), (Date) x);
						else 
							return cb.ge(join.join(path[len - 1]), (Number) x);
					}
					else {
						if (filter.fieldType().isAssignableFrom(Date.class)) 
							return cb.greaterThanOrEqualTo(join.get(path[len - 1]), (Date) y);
						else 
							return cb.ge(join.get(path[len - 1]), (Number) y);
					}
				}

				if (Collection.class.isAssignableFrom(filter.fieldType())) {
					Object x = ((Collection<Object>) y).stream().collect(Collectors.toList()).get(0);
					
					if (x.getClass().isAssignableFrom(Date.class)) 
						return cb.greaterThanOrEqualTo(root.join(path[len - 1]), (Date) x);
					else 
						return cb.ge(root.join(path[len - 1]), (Number) x);
				}
				else {
					if (filter.fieldType().isAssignableFrom(Date.class)) 
						return cb.greaterThanOrEqualTo(root.get(path[len - 1]), (Date) y);
					else 
						return cb.ge(root.get(path[len - 1]), (Number) y);
				}
			};

		case LESS_OR_EQUAL:
			return (root, query, cb) -> {
				String[] path = filter.fieldName().split("\\.");
				Object y = CustomCaster.castTo(filter.fieldType(), filter.value());
				int len = path.length;

				if (len > 1) {
					Join<Object, Object> join = root.join(path[0]);

					if (len > 1) {
						for (int i = 1; i < len - 1; i++) {
							join = join.join(path[i]);
						}
					}
					
					if (Collection.class.isAssignableFrom(filter.fieldType())) {
						Object x = ((Collection<Object>) y).stream().collect(Collectors.toList()).get(0);
						
						if (x.getClass().isAssignableFrom(Date.class)) 
							return cb.lessThanOrEqualTo(join.join(path[len - 1]), (Date) x);
						else 
							return cb.le(join.join(path[len - 1]), (Number) x);
					}
					else {
						if (filter.fieldType().isAssignableFrom(Date.class)) 
							return cb.lessThanOrEqualTo(join.get(path[len - 1]), (Date) y);
						else 
							return cb.le(join.get(path[len - 1]), (Number) y);
					}
				}

				if (Collection.class.isAssignableFrom(filter.fieldType())) {
					Object x = ((Collection<Object>) y).stream().collect(Collectors.toList()).get(0);
					
					if (x.getClass().isAssignableFrom(Date.class)) 
						return cb.lessThanOrEqualTo(root.join(path[len - 1]), (Date) x);
					else 
						return cb.le(root.join(path[len - 1]), (Number) x);
				}
				else {
					if (filter.fieldType().isAssignableFrom(Date.class)) 
						return cb.lessThanOrEqualTo(root.get(path[len - 1]), (Date) y);
					else 
						return cb.le(root.get(path[len - 1]), (Number) y);
				}
			};

		case BETWEEN:
			return (root, query, cb) -> {
				String[] path = filter.fieldName().split("\\.");
				List<Object> y = CustomCaster.castTo(filter.fieldType(), filter.values());
				int len = path.length;

				if (len > 1) {
					Join<Object, Object> join = root.join(path[0]);

					for (int i = 1; i < len - 1; i++) {
						join = join.join(path[i]);
					}
					
					if (Collection.class.isAssignableFrom(filter.fieldType())) {
						Object x1 = ((Collection<Object>) y.get(0)).stream().collect(Collectors.toList()).get(0);
						Object x2 = ((Collection<Object>) y.get(1)).stream().collect(Collectors.toList()).get(0);
						
						if (x1.getClass().isAssignableFrom(Date.class)) 
							return cb.and(
									cb.lessThanOrEqualTo(join.join(path[len - 1]), (Date) x1),
									cb.greaterThanOrEqualTo(join.join(path[len - 1]), (Date) x2));
						else 
							return cb.and(
									cb.le(join.join(path[len - 1]), (Number) x1),
									cb.ge(join.join(path[len - 1]), (Number) x2));
					}
					else {
						if (filter.fieldType().isAssignableFrom(Date.class)) 
							return cb.and(
									cb.lessThanOrEqualTo(join.get(path[len - 1]), (Date) y.get(1)),
									cb.greaterThanOrEqualTo(join.get(path[len - 1]), (Date) y.get(0)));
						else 
							return cb.and(
									cb.le(join.get(path[len - 1]), (Number) y.get(1)),
									cb.ge(join.get(path[len - 1]), (Number) y.get(0)));
					}
				}

				if (Collection.class.isAssignableFrom(filter.fieldType())) {
					Object x1 = ((Collection<Object>) y.get(0)).stream().collect(Collectors.toList()).get(0);
					Object x2 = ((Collection<Object>) y.get(1)).stream().collect(Collectors.toList()).get(0);
					
					if (x1.getClass().isAssignableFrom(Date.class)) 
						return cb.and(
								cb.lessThanOrEqualTo(root.join(path[len - 1]), (Date) x1),
								cb.greaterThanOrEqualTo(root.join(path[len - 1]), (Date) x2));
					else 
						return cb.and(
								cb.le(root.join(path[len - 1]), (Number) x1),
								cb.ge(root.join(path[len - 1]), (Number) x2));

				}
				else {
					if (filter.fieldType().isAssignableFrom(Date.class)) 
						return cb.and(
								cb.lessThanOrEqualTo(root.get(path[len - 1]), (Date) y.get(1)),
								cb.greaterThanOrEqualTo(root.get(path[len - 1]), (Date) y.get(0)));
					else 
						return cb.and(
								cb.le(root.get(path[len - 1]), (Number) y.get(1)),
								cb.ge(root.get(path[len - 1]), (Number) y.get(0)));
				}
			};

		case LIKE:
			return (root, query, cb) -> {
				String[] path = filter.fieldName().split("\\.");
				Object y = CustomCaster.castTo(filter.fieldType(), filter.value());
				int len = path.length;

				if (len > 1) {
					Join<Object, Object> join = root.join(path[0]);

					if (len > 1) {
						for (int i = 1; i < len - 1; i++) {
							join = join.join(path[i]);
						}
					}
					
					if (Collection.class.isAssignableFrom(filter.fieldType())) {
						Collection<Object> set = (Collection<Object>) y;
						Predicate where = cb.disjunction();
						for (Object x : set) {
							where = cb.or(where, cb.like(join.join(path[len - 1]), "%" + x + "%"));
						}
						return where;
					}
					else 
						return cb.like(join.get(path[len - 1]), "%" + y + "%");
				}

				if (Collection.class.isAssignableFrom(filter.fieldType())) {
					Collection<Object> set = (Collection<Object>) y;
					Predicate where = cb.disjunction();
					for (Object x : set) {
						where = cb.or(where, cb.like(root.join(path[len - 1]), "%" + x + "%"));
					}
					return where;
				}
				else 
					return cb.like(root.get(path[len - 1]), "%" + y + "%");
			};

		case ENDS_WITH:
			return (root, query, cb) -> {
				String[] path = filter.fieldName().split("\\.");
				Object y = CustomCaster.castTo(filter.fieldType(), filter.value());
				int len = path.length;

				if (len > 1) {
					Join<Object, Object> join = root.join(path[0]);

					if (len > 1) {
						for (int i = 1; i < len - 1; i++) {
							join = join.join(path[i]);
						}
					}
					
					if (Collection.class.isAssignableFrom(filter.fieldType())) {
						Collection<Object> set = (Collection<Object>) y;
						Predicate where = cb.disjunction();
						for (Object x : set) {
							where = cb.or(where, cb.like(join.join(path[len - 1]), "%" + x));
						}
						return where;
					}
					else 
						return cb.like(join.get(path[len - 1]), "%" + y);
				}

				if (Collection.class.isAssignableFrom(filter.fieldType())) {
					Collection<Object> set = (Collection<Object>) y;
					Predicate where = cb.disjunction();
					for (Object x : set) {
						where = cb.or(where, cb.like(root.join(path[len - 1]), "%" + x));
					}
					return where;
				}
				else 
					return cb.like(root.get(path[len - 1]), "%" + y);
			};

		case STARTS_WITH:
			return (root, query, cb) -> {
				String[] path = filter.fieldName().split("\\.");
				Object y = CustomCaster.castTo(filter.fieldType(), filter.value());
				int len = path.length;

				if (len > 1) {
					Join<Object, Object> join = root.join(path[0]);

					if (len > 1) {
						for (int i = 1; i < len - 1; i++) {
							join = join.join(path[i]);
						}
					}
					
					if (Collection.class.isAssignableFrom(filter.fieldType())) {
						Collection<Object> set = (Collection<Object>) y;
						Predicate where = cb.disjunction();
						for (Object x : set) {
							where = cb.or(where, cb.like(join.join(path[len - 1]), x + "%"));
						}
						return where;
					}
					else 
						return cb.like(join.get(path[len - 1]), y + "%");
				}

				if (Collection.class.isAssignableFrom(filter.fieldType())) {
					Collection<Object> set = (Collection<Object>) y;
					Predicate where = cb.disjunction();
					for (Object x : set) {
						where = cb.or(where, cb.like(root.join(path[len - 1]), x + "%"));
					}
					return where;
				}
				else 
					return cb.like(root.get(path[len - 1]), y + "%");
			};

		case IN:
			return (root, query, cb) -> {
				String[] path = filter.fieldName().split("\\.");
				List<Object> y = CustomCaster.castTo(filter.fieldType(), filter.values());
				int len = path.length;

				if (len > 1) {
					Join<Object, Object> join = root.join(path[0]);

					if (len > 1) {
						for (int i = 1; i < len - 1; i++) {
							join = join.join(path[i]);
						}
					}

					if (Collection.class.isAssignableFrom(filter.fieldType())) {
						Predicate where = cb.disjunction();
						for (Object x : y) {
							where = cb.or(where, cb.equal(join.join(path[len - 1]), x));
						}
						return where;
					}
					else 
						return cb.in(join.get(path[len - 1])).value(y);
				}

				if (Collection.class.isAssignableFrom(filter.fieldType())) {
					Predicate where = cb.disjunction();
					for (Object x : y) {
						where = cb.or(where, cb.equal(root.join(path[len - 1]), x));
					}
					return where;
				}
				else 
					return cb.in(root.get(path[len - 1])).value(y);
			};

		default:
			throw new OperationNotSupportedException(filter.operator());
		}
	}
}