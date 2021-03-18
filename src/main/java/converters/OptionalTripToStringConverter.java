
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.OptionalTrip;

@Component
@Transactional
public class OptionalTripToStringConverter implements Converter<OptionalTrip, String> {

	@Override
	public String convert(final OptionalTrip optionalTrip) {
		String result;

		if (optionalTrip == null)
			result = null;
		else
			result = String.valueOf(optionalTrip.getId());

		return result;
	}

}
