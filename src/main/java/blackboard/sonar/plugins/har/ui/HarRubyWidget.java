package blackboard.sonar.plugins.har.ui;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.WidgetCategory;
import org.sonar.api.web.WidgetProperties;
import org.sonar.api.web.WidgetProperty;
import org.sonar.api.web.WidgetPropertyType;

@UserRole(UserRole.USER)
@Description("Show how to use Ruby Widget API")
@WidgetCategory("HAR Analysis")
@WidgetProperties({
		@WidgetProperty(key = "metric",
    description = "Select Har Metric to display",
    type = WidgetPropertyType.METRIC,
    optional = false
  ),
  @WidgetProperty(key = "parm",
    description = "Test Parameter",
    type = WidgetPropertyType.INTEGER,
    defaultValue = "80"
  )
})
public class HarRubyWidget extends AbstractRubyTemplate implements RubyRailsWidget {

  public String getId() {
    return "har_widget";
  }

  public String getTitle() {
    return "The HAR Analyzer Widget";
  }

  @Override
  protected String getTemplatePath() {
    return "/template/har_widget.html.erb";
  }
}
