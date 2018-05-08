package com.groupbot.models.kaskus;

import java.util.List;
import com.groupbot.models.kaskus.KaskusSendGenericButton;

public class KaskusSendGenericInteractive {
	private List<KaskusSendGenericButton> buttons;
	private String image;
	private String title;
	private String caption;
	
	public KaskusSendGenericInteractive(List<KaskusSendGenericButton> buttons, String image, String title,
			String caption) {
		super();
		this.buttons = buttons;
		this.image = image;
		this.title = title;
		this.caption = caption;
	}

	public List<KaskusSendGenericButton> getButtons() {
		return buttons;
	}
	
	public void setButtons(List<KaskusSendGenericButton> buttons) {
		this.buttons = buttons;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
}