
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.KindOfRoom;

@Component
@Transactional
public class KindOfRoomToStringConverter implements Converter<KindOfRoom, String> {

	@Override
	public String convert(final KindOfRoom kindOfRoom) {
		String result;

		if (kindOfRoom == null)
			result = null;
		else
			result = String.valueOf(kindOfRoom);

		return result;
	}

}
