package nl.novi.theartroom.dto.artworkdto;

public class PaintingOutputDto {

//    private String paintingPaintType;
    private String paintingSurface;
    private String paintingMaterial;
    private Integer paintingDimensionsWidthInCm;
    private Integer paintingDimensionsHeightInCm;

    public PaintingOutputDto() {
    }

    public PaintingOutputDto(String paintingSurface, String paintingMaterial, Integer paintingDimensionsWidthInCm, Integer paintingDimensionsHeightInCm) {
        this.paintingSurface = paintingSurface;
        this.paintingMaterial = paintingMaterial;
        this.paintingDimensionsWidthInCm = paintingDimensionsWidthInCm;
        this.paintingDimensionsHeightInCm = paintingDimensionsHeightInCm;
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
