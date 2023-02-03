package com.vili.mrmentoria.engine.validation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Example;

import com.vili.mrmentoria.engine.IEntity;
import com.vili.mrmentoria.engine.exceptions.FieldMessage;
import com.vili.mrmentoria.engine.exceptions.custom.ComponentNotFoundException;
import com.vili.mrmentoria.engine.exceptions.custom.EnumValueNotFoundException;
import com.vili.mrmentoria.engine.repositories.IRepository;
import com.vili.mrmentoria.util.ContextProvider;

public interface IValidator<E extends IEntity> {

	ConstraintValidatorContext getContext();
	
	void setContext(ConstraintValidatorContext context);
	
	List<FieldMessage> getErrors();
		
	default void addError(FieldMessage error) {
		getErrors().add(error);
	}

	default void addErrors(List<FieldMessage> errors) {
		getErrors().addAll(errors);
	}

	default boolean after(String field, Date value, Date min, boolean required) {
		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		} else {
			if (value.after(min)) {
				addError(new FieldMessage(field, "Must be after " + min));
				return false;
			}
		}

		return true;
	}

	default boolean before(String field, Date value, Date max, boolean required) {
		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		} else {
			if (value.before(max)) {
				addError(new FieldMessage(field, "Must be before " + max));
				return false;
			}
		}

		return true;
	}

	default boolean email(String field, String value, boolean required) {
		String pattern  = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"; // RFC 5322

		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		} else {
			if (!Pattern.compile(pattern).matcher(value).matches()) {
				addError(new FieldMessage(field, "Invalid email"));
				return false;
			}
		}
		
		return true;
	}

	default <T extends IRepository<U>, U extends IEntity> IEntity entityId(String field, Long id, T repo, boolean required) {
		if (id == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return null;
		} else {
			Optional<U> aux = repo.findById(id);

			if (aux.isEmpty()) {
				List<U> list = repo.findAll();
				String msgClass = list.isEmpty() ? list.get(0).getClass().getSimpleName() : "MissingClass";
				addError(new FieldMessage(field, "Resource not found: [" + msgClass + "(id=" + id + ")]"));
				return null;
			} else
				return aux.get();
		}
	}

	default <T extends IRepository<U>, U extends IEntity> List<IEntity> entityIds(String field, List<Long> ids, T repo, boolean required) {
		if (ids == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return null;
		} else {
			if (ids.size() == 0) {
				addError(new FieldMessage(field, "Must not be empty"));
				return null;
			} else {
				List<IEntity> entities = new ArrayList<>();
				String msg = "Resource(s) not found: [msgClass(ids={";
				boolean isError = false;
				
				for (Long id : ids) {
					Optional<U> aux = repo.findById(id);
					
					if (aux.isEmpty()) {
						isError = true;
						msg += id + ", ";
					} else {
						entities.add(aux.get());
					}
				}
				
				if (isError) {
					List<U> list = repo.findAll();
					String msgClass = list.isEmpty() ? list.get(0).getClass().getSimpleName() : "MissingClass";
					msg = (msg.substring(0, msg.lastIndexOf(", ")) + "})]").replace("msgClass", msgClass);
					addError(new FieldMessage(field, msg));
					return null;
				} else
					return entities;
			}
		}
	}

	default boolean enumValue(String field, Integer value, Class<?> target, boolean required) {
		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		}

		return enumValueTest(value, target, field);
	}
	
	default boolean enumValues(String field, List<Integer> values, Class<?> target, boolean required) {
		if (values == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		}
		
		if (values.size() == 0) {
			addError(new FieldMessage(field, "Must not be empty"));
			return false;
		} else {
			boolean isError = false;
			
			for (Integer value : values) {
				if (!enumValueTest(value, target, field))
					isError = true;
			}
			
			return !isError;
		}
	}

	default boolean future(String field, Date value, boolean required) {
		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		} else {
			if (value.before(new Date())) {
				addError(new FieldMessage(field, "Must be future date"));
				return false;
			}
		}

		return true;
	}

	default boolean in(String field, Integer value, List<Integer> range, boolean required) {
		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		} else {
			if (!range.contains(value)) {
				addError(new FieldMessage(field, "Must be on collection: " + range));
				return false;
			}
		}

		return true;
	}
	
	default boolean length(String field, String value, Integer min, Integer max, boolean required) {
		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		} else {
			if (value.length() < min && value.length() > max) {
				addError(new FieldMessage(field, "Must be between " + min + " and " + max + " characters"));
				 return false;
			}
		}

		return true;
	}
	
	default boolean max(String field, Number value, Number max, boolean required) {
		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		} else {
			if (Collection.class.isAssignableFrom(value.getClass())) {
				if (max instanceof Integer) {
					if (((Collection<?>) value).size() > (Integer) max) {
						addError(new FieldMessage(field, "Must have a maximum of " + max + " element(s)"));
						return false;
					}
				} else {
					addError(new FieldMessage(field, "Method parameter (min) must be an Integer if parameter (value) is a collection"));
					return false;
				}
			} else {
				if (value instanceof Number) {
					if (value.getClass().equals(max.getClass())) {
						addError(new FieldMessage(field, "Method parameter (min) must be of same class of method parameter (value)"));
						return false;
					} else {
						if (value instanceof Integer) {
							if ((Integer) value > (Integer) max) {
								addError(new FieldMessage(field, "Must be less than or equal " + max));
								return false;
							}
						} else if (value instanceof Long) {
							if ((Long) value > (Long) max) {
								addError(new FieldMessage(field, "Must be less than or equal " + max));
								return false;
							}
						} else if (value instanceof Double) {
							if ((Double) value > (Double) max) {
								addError(new FieldMessage(field, "Must be less than or equal " + max));
								return false;
							}
						} else {
							addError(new FieldMessage(field, "Method not implemented for class: " + value.getClass().getSimpleName()));
							return false;
						}
					}
				} else {
					addError(new FieldMessage(field, "Method parameter (value) can only be of classes Collection or Number, or their subclasses"));
					return false;

				}
			}
		}

		return true;
	}

	default boolean min(String field, Object value, Number min, boolean required) {
		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		} else {
			if (Collection.class.isAssignableFrom(value.getClass())) {
				if (min instanceof Integer) {
					if (((Collection<?>) value).size() < (Integer) min) {
						addError(new FieldMessage(field, "Must have a minimum of " + min + " element(s)"));
						return false;
					}
				} else {
					addError(new FieldMessage(field, "Method parameter (min) must be an Integer if parameter (value) is a collection"));
					return false;
				}
			} else {
				if (value instanceof Number) {
					if (value.getClass().equals(min.getClass())) {
						addError(new FieldMessage(field, "Method parameter (min) must be of same class of method parameter (value)"));
						return false;
					} else {
						if (value instanceof Integer) {
							if ((Integer) value < (Integer) min) {
								addError(new FieldMessage(field, "Must be greater than or equal " + min));
								return false;
							}
						} else if (value instanceof Long) {
							if ((Long) value < (Long) min) {
								addError(new FieldMessage(field, "Must be greater than or equal " + min));
								return false;
							}
						} else if (value instanceof Double) {
							if ((Double) value < (Double) min) {
								addError(new FieldMessage(field, "Must be greater than or equal " + min));
								return false;
							}
						} else {
							addError(new FieldMessage(field, "Method not implemented for class: " + value.getClass().getSimpleName()));
							return false;
						}
					}
				} else {
					addError(new FieldMessage(field, "Method parameter (value) can only be of classes Collection or Number, or their subclasses"));
					return false;

				}
			}
		}

		return true;
	}

	default boolean notEmpty(String field, Object value, boolean required) {
		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		} else {
			if (Collection.class.isAssignableFrom(value.getClass())) {
				if (((Collection<?>) value).isEmpty()) {
					if (required)
						addError(new FieldMessage(field, "Must not be empty"));
					 
					return false;
				}
			} else {
				if (value.toString().isBlank()) {
					addError(new FieldMessage(field, "Must not be empty"));
					 return false;
				}	
			}
			
		}

		return true;
	}
	
	default boolean notIn(String field, Integer value, List<Integer> range, boolean required) {
		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		} else {
			if (range.contains(value)) {
				addError(new FieldMessage(field, "Must not be on collection: " + range));
				return false;
			}
		}

		return true;
	}

	default boolean notNull(String field, Object value, boolean required) {
		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		}

		return true;
	}

	default boolean pattern(String field, String value, String pattern, boolean required) {
		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		} else {
			if (!Pattern.compile(pattern).matcher(value).matches()) {
				addError(new FieldMessage(field, "Must match RegEx pattern: " + pattern));
				return false;
			}
		}

		return true;
	}
	
	default boolean positive(String field, Number value, boolean required) {
		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		} else {
			if (value.getClass().isAssignableFrom(Integer.class)) {
				if (((Integer) value) <= 0) {
					addError(new FieldMessage(field, "Must be positive"));
					return false;
				}
			}
			
			if (value.getClass().isAssignableFrom(Long.class)) {
				if (((Long) value) <= 0) {
					addError(new FieldMessage(field, "Must be positive"));
					return false;
				}
			}

			if (value.getClass().isAssignableFrom(Double.class)) {
				if (((Double) value) <= 0) {
					addError(new FieldMessage(field, "Must be positive"));
					return false;
				}
			}
		}

		return true;
	}

	default boolean positiveOrZero(String field, Object value, boolean required) {
		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		} else {
			if (value.getClass().isAssignableFrom(Integer.class)) {
				if (((Integer) value) < 0) {
					addError(new FieldMessage(field, "Must be positive or zero"));
					return false;
				}
			}
			
			if (value.getClass().isAssignableFrom(Long.class)) {
				if (((Long) value) < 0) {
					addError(new FieldMessage(field, "Must be positive or zero"));
					return false;
				}
			}

			if (value.getClass().isAssignableFrom(Double.class)) {
				if (((Double) value) < 0) {
					addError(new FieldMessage(field, "Must be positive or zero"));
					return false;
				}
			}
		}

		return true;
	}

	default boolean range(String field, Object value, Object min, Object max, boolean required) {
		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		} else {
			if (!value.getClass().equals(min.getClass()) || !value.getClass().equals(max.getClass())) {
				addError(new FieldMessage(field, "Method parameters (min, max) must be of same class of method parameter (value)"));
				return false;
			} else {
				if (value.getClass().isAssignableFrom(Date.class)) {
					if (((Date) value).before(((Date) min))  || ((Date) value).after(((Date) max))) {
						addError(new FieldMessage(field, "Must be between " + min + " and " + max));
						return false;
					}
				} else if (value.getClass().isAssignableFrom(Integer.class)) {
					if (((Integer) value) < ((Integer) min) || ((Integer) value) > ((Integer) max)) {
						addError(new FieldMessage(field, "Must be between " + min + " and " + max));
						return false;
					}
				} else if (value.getClass().isAssignableFrom(Long.class)) {
					if (((Long) value) < ((Long) min) || ((Long) value) > ((Long) max)) {
						addError(new FieldMessage(field, "Must be between " + min + " and " + max));
						return false;
					}
				} else if (value.getClass().isAssignableFrom(Double.class)) {
					if (((Double) value) < ((Double) min) || ((Double) value) > ((Double) max)) {
						addError(new FieldMessage(field, "Must be between " + min + " and " + max));
						return false;
					}
				} else {
					addError(new FieldMessage(field, "Cannot validate ranges for " + value.getClass() + " values"));
					return false;
				}
			}
		}

		return true;
	}
	
	default boolean size(String field, Object value, int size, boolean required) {
		if (value == null) {
			if (required)
				addError(new FieldMessage(field, "Must not be null"));
			
			return false;
		} else {
			if (Collection.class.isAssignableFrom(value.getClass())) {
				if (((Collection<?>) value).size() != size) {
					addError(new FieldMessage(field, "Must have " + size + " element(s)"));
					return false;
				}
			} else {
				if (value.toString().length() != size) {
				addError(new FieldMessage(field, "Must have " + size + " character(s)"));
				 return false;
				}
			}
		}

		return true;
	}

	default List<E> unique(String field, Example<E> example, IRepository<IEntity> repo, boolean required) {
		Class<?> probeType = example.getProbeType();
		List<E> ret = repo.findAll(example);
		String method = "get" + field.substring(0, 1).toUpperCase() + field.substring(1, field.length());
		Object value = null;
		Class<?> temp = probeType;

		while (!temp.equals(Object.class)) {
			try {
				value = temp.getDeclaredMethod(method).invoke(example.getProbe());
				break;
			} catch (Exception e) {
				temp = temp.getSuperclass();
			}
		}

		if (temp.equals(Object.class)) {
			addError(new FieldMessage(field, "Method '" + method + "' not found on class '" + temp.getSimpleName() + "'"));
			return new ArrayList<>();
		}
		
		if (value == null) {
			if (required) {
				addError(new FieldMessage(field, "Must not be null"));
				return new ArrayList<>();
			}

			return new ArrayList<>();
		} else {
			if (!ret.isEmpty()) {
				Map<String, Object> fields = mapNonNullOrEmptyValues(example.getProbe());
				String msg = "[";
				
				for (Entry<String, Object> entry : fields.entrySet()) {
					msg += entry.getKey() + "=" + entry.getValue().toString() + ", ";
				}

				msg = msg.substring(0, msg.lastIndexOf(", ")) + "]";
				addError(new FieldMessage(field, "Entity of class '" + probeType.getSimpleName() + "' with field(s) " + msg + " already exists"));
				return new ArrayList<>();
			}
		}

		return ret;
	}
	
	default boolean buildConstraintViolations() {
		for (FieldMessage e : getErrors()) {
			getContext().disableDefaultConstraintViolation();
			getContext().buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getField()).addConstraintViolation();
		}
		
		return getErrors().isEmpty();
	}

	private boolean enumValueTest(Integer code, Class<?> target, String field) {
		try {
			target.getDeclaredMethod("toEnum", Integer.class).invoke(target, code);
		} catch (Exception e) {
			if (e.getCause().getClass().equals(EnumValueNotFoundException.class))
				addError(new FieldMessage(field, e.getCause().getMessage()));
			else
				addError(new FieldMessage(field, "Method 'public static " + target.getSimpleName() + " toEnum(Integer code)' not implemented on class '" + target.getSimpleName() + "'"));
			
			return false;
		}
		
		return true;
	}
	
	private Map<String, Object> mapNonNullOrEmptyValues(IEntity probe) {
		Map<String, Object> map = new HashMap<>();
		Class<?> type = probe.getClass();

		while (!type.equals(Object.class)) {
			for (Method method : type.getDeclaredMethods()) {
				if (method.getName().startsWith("get")) {
					String field = method.getName().substring(3);
					String key = field.substring(0, 1).toLowerCase() + field.substring(1, field.length());
					try {
						Object value = method.invoke(probe);
						
						if (value != null) {
							if (IEntity.class.isAssignableFrom(value.getClass())) {
								key = key + ".id";
								value = ((IEntity) value).getId();
							} else if (Collection.class.isAssignableFrom(value.getClass())) {
								if (((Collection<?>) value).isEmpty())
									continue;
							}
							
							map.put(key, value);
						}
					} catch (Exception e) {
						addError(new FieldMessage(key, e.getMessage()));
					}
				}
			}

			type = type.getSuperclass();
		}
			
		return map;
	}

	static List<FieldMessage> handleErrors(List<FieldMessage> errors, String parentField) {
		List<FieldMessage> aux = new ArrayList<>();
		
		errors.forEach(msg -> {
			msg.setField(parentField + "." + msg.getField());
			aux.add(msg);
		});
		
		return aux;
	}
	
	static IRepository<IEntity> getRepository(Class<?> type) {
		String aux = type.getSimpleName() + "Repository";
		String bean = aux.substring(0, 1).toLowerCase() + aux.substring(1);
		return getBean(bean);
	}
	
	@SuppressWarnings("unchecked")
	private static <E extends IEntity> IRepository<E> getBean(String name) {
		try {
			ApplicationContext context = ContextProvider.getApplicationContext();
			return (IRepository<E>) context.getBean(name);
		} catch (BeansException e) {
			throw new ComponentNotFoundException("Component '" + name + "' not found");
		}
    }

}
