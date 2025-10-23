package poly.edu.service;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

public interface MailService {
	@Data
	@Builder
	@Accessors(chain = true)
	public static class Mail {
		@Builder.Default
		private String from = "WebShop <web-shop@gmail.com>";
		private String to;
		private String cc;
		private String bcc;
		private String subject;
		private String body;
		private String filenames;
	}

	void send(Mail mail);

	default void send(String to, String subject, String body) {
		Mail mail = Mail.builder().to(to).subject(subject).body(body).build();
		this.send(mail);
	}
}
