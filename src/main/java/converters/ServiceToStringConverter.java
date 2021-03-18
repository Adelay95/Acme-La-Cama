
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Services;

@Component
@Transactional
public class ServiceToStringConverter implements Converter<Services, String> {

	@Override
	public String convert(final Services service) {
		String result;

		if (service == null)
			result = null;
		else
			result = String.valueOf(service.getId());

		return result;
	}

}
