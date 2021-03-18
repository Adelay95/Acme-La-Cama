
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.BillLine;

@Component
@Transactional
public class BillLineToStringConverter implements Converter<BillLine, String> {

	@Override
	public String convert(final BillLine billLine) {
		String result;

		if (billLine == null)
			result = null;
		else
			result = String.valueOf(billLine.getId());

		return result;
	}

}
