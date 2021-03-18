
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.State;

@Component
@Transactional
public class StateToStringConverter implements Converter<State, String> {

	@Override
	public String convert(final State state) {
		String result;

		if (state == null)
			result = null;
		else
			result = String.valueOf(state);

		return result;
	}

}
