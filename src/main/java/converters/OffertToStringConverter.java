
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Offert;

@Component
@Transactional
public class OffertToStringConverter implements Converter<Offert, String> {

	@Override
	public String convert(final Offert offert) {
		String result;

		if (offert == null)
			result = null;
		else
			result = String.valueOf(offert.getId());

		return result;
	}

}
