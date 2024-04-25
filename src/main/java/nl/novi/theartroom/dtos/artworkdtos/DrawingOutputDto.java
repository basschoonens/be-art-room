package nl.novi.theartroom.dtos.artworkdtos;

public class DrawingOutputDto {

    private String drawingDrawType;
    private String drawingSurface;
    private String drawingMaterial;
    private Integer drawingDimensionsWidthInCm;
    private Integer drawingDimensionsHeightInCm;

    public String getDrawingDrawType() {
        return drawingDrawType;
    }

    public void setDrawingDrawType(String drawingDrawType) {
        this.drawingDrawType = drawingDrawType;
    }

    public String getDrawingSurface() {
        return drawingSurface;
    }

    public void setDrawingSurface(String drawingSurface) {
        this.drawingSurface = drawingSurface;
    }

    public String getDrawingMaterial() {
        return drawingMaterial;
    }

    public void setDrawingMaterial(String drawingMaterial) {
        this.drawingMaterial = drawingMaterial;
    }

    public Integer getDrawingDimensionsWidthInCm() {
        return drawingDimensionsWidthInCm;
    }

    public void setDrawingDimensionsWidthInCm(Integer drawingDimensionsWidthInCm) {
        this.drawingDimensionsWidthInCm = drawingDimensionsWidthInCm;
    }

    public Integer getDrawingDimensionsHeightInCm() {
        return drawingDimensionsHeightInCm;
    }

    public void setDrawingDimensionsHeightInCm(Integer drawingDimensionsHeightInCm) {
        this.drawingDimensionsHeightInCm = drawingDimensionsHeightInCm;
    }
}
