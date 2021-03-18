
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.HotelRepository;
import domain.Hotel;

@Component
@Transactional
public class StringToHotelConverter implements Converter<String, Hotel> {

	@Autowired
	HotelRepository	hotelRepository;


	@Override
	public Hotel convert(final String text) {
		Hotel result;
		int id;
		if ((text == "") || (text == null))
			result = null;
		try {
			id = Integer.valueOf(text);
			result = this.hotelRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
