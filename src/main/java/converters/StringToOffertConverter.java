
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.OffertRepository;
import domain.Offert;

@Component
@Transactional
public class StringToOffertConverter implements Converter<String, Offert> {

	@Autowired
	OffertRepository	offertRepository;


	@Override
	public Offert convert(final String text) {
		Offert result;
		int id;
		if ((text == "") || (text == null))
			result = null;
		try {
			id = Integer.valueOf(text);
			result = this.offertRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
