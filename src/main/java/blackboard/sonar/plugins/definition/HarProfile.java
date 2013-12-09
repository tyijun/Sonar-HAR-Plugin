package blackboard.sonar.plugins.definition;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.profiles.XMLProfileParser;
import org.sonar.api.utils.ValidationMessages;

public class HarProfile extends ProfileDefinition {

	public static final String RULES_PATH = "conf/har_rules.xml";

	private final XMLProfileParser profileParser;

	public HarProfile(XMLProfileParser profileParser) {
		this.profileParser = profileParser;
	}

	@Override
	public RulesProfile createProfile(ValidationMessages validation) {
		// TODO Auto-generated method stub
		Reader reader = new InputStreamReader(HarProfile.class.getClassLoader().getResourceAsStream(RULES_PATH), Charset.forName(CharEncoding.UTF_8));

		try {
			RulesProfile profile = profileParser.parse(reader, validation);
			profile.setDefaultProfile(true);
			return profile;
		} finally {
			IOUtils.closeQuietly(reader);
		}
	}

}
