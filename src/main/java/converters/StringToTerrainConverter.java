
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Terrain;

@Component
@Transactional
public class StringToTerrainConverter implements Converter<String, Terrain> {

	@Override
	public Terrain convert(final String text) {
		Terrain result;

		try {
			result = Terrain.valueOf(text);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
