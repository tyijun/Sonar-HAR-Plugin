package blackboard.sonar.plugins.har.ui;

import org.sonar.api.web.NavigationSection;
import org.sonar.api.web.Page;
import org.sonar.api.web.UserRole;

@NavigationSection(NavigationSection.CONFIGURATION)
@UserRole(UserRole.ADMIN)
public final class HarConsolePage implements Page {
	public String getId() {
		// URL of the controller
		return "/har_console/index";
	}

	public String getTitle() {
		return "HAR Analyzer Console";
	}
}
