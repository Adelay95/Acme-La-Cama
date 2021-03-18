
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.State;

@Component
@Transactional
public class StringToStateConverter implements Converter<String, State> {

	@Override
	public State convert(final String text) {
		State result;

		try {
			result = State.valueOf(text);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
