
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RoomRepository;
import domain.Room;

@Component
@Transactional
public class StringToRoomConverter implements Converter<String, Room> {

	@Autowired
	RoomRepository	roomRepository;


	@Override
	public Room convert(final String text) {
		Room result;
		int id;
		if ((text == "") || (text == null))
			result = null;
		try {
			id = Integer.valueOf(text);
			result = this.roomRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
