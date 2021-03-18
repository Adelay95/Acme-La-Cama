
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Terrain;

@Component
@Transactional
public class TerrainToStringConverter implements Converter<Terrain, String> {

	@Override
	public String convert(final Terrain terrain) {
		String result;

		if (terrain == null)
			result = null;
		else
			result = String.valueOf(terrain);

		return result;
	}

}
