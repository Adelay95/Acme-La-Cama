
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.KindOfRoom;

@Component
@Transactional
public class StringToKindOfRoomConverter implements Converter<String, KindOfRoom> {

	@Override
	public KindOfRoom convert(final String text) {
		KindOfRoom result;

		try {
			result = KindOfRoom.valueOf(text);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
