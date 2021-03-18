
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Season;

@Component
@Transactional
public class StringToSeasonConverter implements Converter<String, Season> {

	@Override
	public Season convert(final String text) {
		Season result;

		try {
			result = Season.valueOf(text);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
