package nl.novi.theartroom.dtos;

public class ArtworkOutputAdminDto {
        private Long id;
        private String title;
        private String artist;
        private String description;
        private Integer dateCreated;
        private Double galleryBuyingPrice;
        private String edition;
        private String imageUrl;
        private String artworkType;

        // Painting fields
        private String paintingPaintType;
        private String paintingSurface;
        private String paintingMaterial;
        private Integer paintingDimensionsWidthInCm;
        private Integer paintingDimensionsHeightInCm;

        // Drawing fields
        private String drawingDrawType;
        private String drawingSurface;
        private String drawingMaterial;
        private Integer drawingDimensionsWidthInCm;
        private Integer drawingDimensionsHeightInCm;


        public ArtworkOutputAdminDto() {
        }

        public ArtworkOutputAdminDto(Long id, String title, String artist, String description, Integer dateCreated, Double galleryBuyingPrice, String edition, String imageUrl, String artworkType, String paintingPaintType, String paintingSurface, String paintingMaterial, Integer paintingDimensionsWidthInCm, Integer paintingDimensionsHeightInCm, String drawingDrawType, String drawingSurface, String drawingMaterial, Integer drawingDimensionsWidthInCm, Integer drawingDimensionsHeightInCm) {
            this.id = id;
            this.title = title;
            this.artist = artist;
            this.description = description;
            this.dateCreated = dateCreated;
            this.galleryBuyingPrice = galleryBuyingPrice;
            this.edition = edition;
            this.imageUrl = imageUrl;
            this.artworkType = artworkType;
            this.paintingPaintType = paintingPaintType;
            this.paintingSurface = paintingSurface;
            this.paintingMaterial = paintingMaterial;
            this.paintingDimensionsWidthInCm = paintingDimensionsWidthInCm;
            this.paintingDimensionsHeightInCm = paintingDimensionsHeightInCm;
            this.drawingDrawType = drawingDrawType;
            this.drawingSurface = drawingSurface;
            this.drawingMaterial = drawingMaterial;
            this.drawingDimensionsWidthInCm = drawingDimensionsWidthInCm;
            this.drawingDimensionsHeightInCm = drawingDimensionsHeightInCm;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(Integer dateCreated) {
            this.dateCreated = dateCreated;
        }

        public Double getGalleryBuyingPrice() {
            return galleryBuyingPrice;
        }

        public void setGalleryBuyingPrice(Double galleryBuyingPrice) {
            this.galleryBuyingPrice = galleryBuyingPrice;
        }

        public String getEdition() {
            return edition;
        }

        public void setEdition(String edition) {
            this.edition = edition;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getArtworkType() {
            return artworkType;
        }

        public void setArtworkType(String artworkType) {
            this.artworkType = artworkType;
        }

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


