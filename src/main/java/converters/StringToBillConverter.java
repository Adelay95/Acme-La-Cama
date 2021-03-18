
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BillRepository;
import domain.Bill;

@Component
@Transactional
public class StringToBillConverter implements Converter<String, Bill> {

	@Autowired
	BillRepository	billRepository;


	@Override
	public Bill convert(final String text) {
		Bill result;
		int id;
		if ((text == "") || (text == null))
			result = null;
		else{
		try {
			id = Integer.valueOf(text);
			result = this.billRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}}

		return result;
	}

}
