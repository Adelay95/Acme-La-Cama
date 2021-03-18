
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Season;

@Component
@Transactional
public class SeasonToStringConverter implements Converter<Season, String> {

	@Override
	public String convert(final Season season) {
		String result;

		if (season == null)
			result = null;
		else
			result = String.valueOf(season);

		return result;
	}

}
