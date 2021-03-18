
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Hotel;

@Component
@Transactional
public class HotelToStringConverter implements Converter<Hotel, String> {

	@Override
	public String convert(final Hotel hotel) {
		String result;

		if (hotel == null)
			result = null;
		else
			result = String.valueOf(hotel.getId());

		return result;
	}

}
