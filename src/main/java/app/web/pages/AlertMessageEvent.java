package app.web.pages;

import lombok.Data;
import org.apache.wicket.ajax.AjaxRequestTarget;

@Data
public class AlertMessageEvent {

  AjaxRequestTarget target;
  String msg;
  public AlertMessageEvent(AjaxRequestTarget target, String msg) {
    this.target=target;
    this.msg = msg;
  }



}
