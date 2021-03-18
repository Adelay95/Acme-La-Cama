
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Brand;

@Component
@Transactional
public class StringToBrandConverter implements Converter<String, Brand> {

	@Override
	public Brand convert(final String text) {
		Brand result;

		try {
			result = Brand.valueOf(text);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
