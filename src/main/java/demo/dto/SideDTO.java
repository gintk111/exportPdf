package demo.dto;

import java.util.List;

public class SideDTO {
	private int side;

	private List<ElementDTO> elements;

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public List<ElementDTO> getElements() {
		return elements;
	}

	public void setElements(List<ElementDTO> elements) {
		this.elements = elements;
	}

}
