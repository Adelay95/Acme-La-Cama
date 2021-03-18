
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Global;

@Component
@Transactional
public class GlobalToStringConverter implements Converter<Global, String> {

	@Override
	public String convert(final Global global) {
		String result;

		if (global == null)
			result = null;
		else
			result = String.valueOf(global.getId());

		return result;
	}

}
