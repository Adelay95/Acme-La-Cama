
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.KindOfOffert;

@Component
@Transactional
public class StringToKindOfOffertConverter implements Converter<String, KindOfOffert> {

	@Override
	public KindOfOffert convert(final String text) {
		KindOfOffert result;

		try {
			result = KindOfOffert.valueOf(text);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
