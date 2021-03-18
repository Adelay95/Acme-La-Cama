
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.OptionalTripRepository;
import domain.OptionalTrip;

@Component
@Transactional
public class StringToOptionalTripConverter implements Converter<String, OptionalTrip> {

	@Autowired
	OptionalTripRepository	optionalTripRepository;


	@Override
	public OptionalTrip convert(final String text) {
		OptionalTrip result;
		int id;
		if ((text == "") || (text == null))
			result = null;
		try {
			id = Integer.valueOf(text);
			result = this.optionalTripRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
