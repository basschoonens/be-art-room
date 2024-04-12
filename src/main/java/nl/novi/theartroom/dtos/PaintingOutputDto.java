package nl.novi.theartroom.dtos;

public class PaintingOutputDto {

    private String paintingPaintType;
    private String paintingSurface;
    private String paintingMaterial;
    private Integer paintingDimensionsWidthInCm;
    private Integer paintingDimensionsHeightInCm;


    public String getPaintingPaintType() {
        return paintingPaintType;
    }

    public void setPaintingPaintType(String paintingPaintType) {
        this.paintingPaintType = paintingPaintType;
    }

    public String getPaintingSurface() {
        return paintingSurface;
    }

    public void setPaintingSurface(String paintingSurface) {
        this.paintingSurface = paintingSurface;
    }

    public String getPaintingMaterial() {
        return paintingMaterial;
    }

    public void setPaintingMaterial(String paintingMaterial) {
        this.paintingMaterial = paintingMaterial;
    }

    public Integer getPaintingDimensionsWidthInCm() {
        return paintingDimensionsWidthInCm;
    }

    public void setPaintingDimensionsWidthInCm(Integer paintingDimensionsWidthInCm) {
        this.paintingDimensionsWidthInCm = paintingDimensionsWidthInCm;
    }

    public Integer getPaintingDimensionsHeightInCm() {
        return paintingDimensionsHeightInCm;
    }

    public void setPaintingDimensionsHeightInCm(Integer paintingDimensionsHeightInCm) {
        this.paintingDimensionsHeightInCm = paintingDimensionsHeightInCm;
    }
}
