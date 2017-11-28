package com.vbd.mapexam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class ShortPathObj {

    @SerializedName("fullPath")
    @Expose
    private List<List<List<Double>>> fullPath = null;
    @SerializedName("pathLength")
    @Expose
    private Integer pathLength;
    @SerializedName("realPlaces")
    @Expose
    private List<Double> realPlaces = null;
    @SerializedName("resultScript")
    @Expose
    private ResultScript resultScript;
    @SerializedName("segments")
    @Expose
    private List<Integer> segments = null;

    public List<List<List<Double>>> getFullPath() {
        return fullPath;
    }

    public void setFullPath(List<List<List<Double>>> fullPath) {
        this.fullPath = fullPath;
    }

    public Integer getPathLength() {
        return pathLength;
    }

    public void setPathLength(Integer pathLength) {
        this.pathLength = pathLength;
    }

    public List<Double> getRealPlaces() {
        return realPlaces;
    }

    public void setRealPlaces(List<Double> realPlaces) {
        this.realPlaces = realPlaces;
    }

    public ResultScript getResultScript() {
        return resultScript;
    }

    public void setResultScript(ResultScript resultScript) {
        this.resultScript = resultScript;
    }

    public List<Integer> getSegments() {
        return segments;
    }

    public void setSegments(List<Integer> segments) {
        this.segments = segments;
    }

    public class ResultScript {

        @SerializedName("leg")
        @Expose
        private List<Leg> leg = null;
        @SerializedName("len")
        @Expose
        private Integer len;

        public List<Leg> getLeg() {
            return leg;
        }

        public void setLeg(List<Leg> leg) {
            this.leg = leg;
        }

        public Integer getLen() {
            return len;
        }

        public void setLen(Integer len) {
            this.len = len;
        }

    }
    public class Leg {

        @SerializedName("step")
        @Expose
        private List<Step> step = null;
        @SerializedName("endX")
        @Expose
        private Double endX;
        @SerializedName("endY")
        @Expose
        private Double endY;
        @SerializedName("found")
        @Expose
        private Boolean found;
        @SerializedName("len")
        @Expose
        private Integer len;
        @SerializedName("startX")
        @Expose
        private Double startX;
        @SerializedName("startY")
        @Expose
        private Double startY;

        public List<Step> getStep() {
            return step;
        }

        public void setStep(List<Step> step) {
            this.step = step;
        }

        public Double getEndX() {
            return endX;
        }

        public void setEndX(Double endX) {
            this.endX = endX;
        }

        public Double getEndY() {
            return endY;
        }

        public void setEndY(Double endY) {
            this.endY = endY;
        }

        public Boolean getFound() {
            return found;
        }

        public void setFound(Boolean found) {
            this.found = found;
        }

        public Integer getLen() {
            return len;
        }

        public void setLen(Integer len) {
            this.len = len;
        }

        public Double getStartX() {
            return startX;
        }

        public void setStartX(Double startX) {
            this.startX = startX;
        }

        public Double getStartY() {
            return startY;
        }

        public void setStartY(Double startY) {
            this.startY = startY;
        }
        public class Step {

            @SerializedName("x")
            @Expose
            private Double x;
            @SerializedName("y")
            @Expose
            private Double y;
            @SerializedName("angle")
            @Expose
            private Integer angle;
            @SerializedName("dir")
            @Expose
            private String dir;
            @SerializedName("len")
            @Expose
            private Integer len;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("start")
            @Expose
            private Integer start;

            public Double getX() {
                return x;
            }

            public void setX(Double x) {
                this.x = x;
            }

            public Double getY() {
                return y;
            }

            public void setY(Double y) {
                this.y = y;
            }

            public Integer getAngle() {
                return angle;
            }

            public void setAngle(Integer angle) {
                this.angle = angle;
            }

            public String getDir() {
                return dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }

            public Integer getLen() {
                return len;
            }

            public void setLen(Integer len) {
                this.len = len;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getStart() {
                return start;
            }

            public void setStart(Integer start) {
                this.start = start;
            }

        }
    }

}








