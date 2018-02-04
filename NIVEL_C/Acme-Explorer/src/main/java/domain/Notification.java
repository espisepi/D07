
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Notification extends DomainEntity {

	// Attributes -------------------------------------------

	private String	ticker;
	private int		gauge;
	private Date	moment;


	@NotBlank
	@Column(unique = true)
	//@Pattern(regexp = "^\\d{2}(0[1-9]{1}|1[0-2]{1})((0|1|2)\\d{1}|3[0-1]{1})\\-[A-Z]{4}$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@Range(min = 1, max = 3)
	public int getGauge() {
		return this.gauge;
	}

	public void setGauge(final int gauge) {
		this.gauge = gauge;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

}
