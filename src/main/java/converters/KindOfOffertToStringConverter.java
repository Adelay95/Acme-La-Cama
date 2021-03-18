
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.KindOfOffert;

@Component
@Transactional
public class KindOfOffertToStringConverter implements Converter<KindOfOffert, String> {

	@Override
	public String convert(final KindOfOffert kindOfOffert) {
		String result;

		if (kindOfOffert == null)
			result = null;
		else
			result = String.valueOf(kindOfOffert);

		return result;
	}

}
