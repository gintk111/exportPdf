package demo.dto;

import java.util.List;

public class TemplateDTO {
	private String name;
	private Integer pattern;
	private List<SideDTO> sides;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPattern() {
		return pattern;
	}

	public void setPattern(Integer pattern) {
		this.pattern = pattern;
	}

	public List<SideDTO> getSides() {
		return sides;
	}

	public void setSides(List<SideDTO> sides) {
		this.sides = sides;
	}

}
