
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.FloutRepository;
import domain.Flout;

@Component
@Transactional
public class StringToFloutConverter implements Converter<String, Flout> {

	@Autowired
	private FloutRepository	floutRepository;


	@Override
	public Flout convert(final String text) {

		Flout result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.floutRepository.findOne(id);

			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
