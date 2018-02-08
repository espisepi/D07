
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Flout;

@Component
@Transactional
public class FloutToStringConverter implements Converter<Flout, String> {

	@Override
	public String convert(final Flout flout) {

		String result;

		if (flout == null)
			result = null;
		else
			result = String.valueOf(flout.getId());

		return result;
	}

}
