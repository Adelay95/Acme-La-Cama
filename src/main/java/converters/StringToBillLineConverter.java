
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BillLineRepository;
import domain.BillLine;

@Component
@Transactional
public class StringToBillLineConverter implements Converter<String, BillLine> {

	@Autowired
	BillLineRepository	billLineRepository;


	@Override
	public BillLine convert(final String text) {
		BillLine result;
		int id;
		if ((text == "") || (text == null))
			result = null;
		try {
			id = Integer.valueOf(text);
			result = this.billLineRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
