
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ReservationRepository;
import domain.Reservation;

@Component
@Transactional
public class StringToReservationConverter implements Converter<String, Reservation> {

	@Autowired
	ReservationRepository	reservationRepository;


	@Override
	public Reservation convert(final String text) {
		Reservation result;
		int id;
		if ((text == "") || (text == null))
			result = null;
		try {
			id = Integer.valueOf(text);
			result = this.reservationRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
