package com.hzdl.teacher.bean.lesson;

import java.util.List;

/**
 * Created by wangshuai on 2017/10/13.
 */

public class CrouseListBean {


    /**
     * code : 200
     * description : 请求成功
     * detail : [{"course_id":1,"part_sort":1,"path":"培训用","childrenPart":[{"course_id":1,"part_sort":0,"path":"培训用-->第一课","videoPath":"","childrenPart":[{"course_id":1,"part_sort":0,"path":"培训用-->第一课-->宫商角徴羽","videoPath":"/video/五线谱与高音谱号.mp4","childrenPart":null,"name":"五线谱与高音谱号.mp4","pid":4146,"id":4152,"has_source":2,"state":1,"type":102}],"name":"宫商角徴羽","pid":4145,"id":4146,"has_source":1,"state":1,"type":1},{"course_id":1,"part_sort":1,"path":"培训用-->第一课","videoPath":"","childrenPart":[{"course_id":1,"part_sort":1,"path":"培训用-->第一课-->从耳德，与歌","videoPath":"/video/欢乐颂.mp4","childrenPart":null,"name":"欢乐颂.mp4","pid":4147,"id":4153,"has_source":2,"state":1,"type":102}],"name":"从耳德，与歌","pid":4145,"id":4147,"has_source":1,"state":1,"type":1},{"course_id":1,"part_sort":2,"path":"培训用-->第一课","videoPath":"","childrenPart":[{"course_id":1,"part_sort":2,"path":"培训用-->第一课-->弹奏","videoPath":"/video/认识黑白键1.mp4","childrenPart":null,"name":"认识黑白键1.mp4","pid":4148,"id":4154,"has_source":2,"state":1,"type":102},{"course_id":1,"part_sort":3,"path":"培训用-->第一课-->弹奏","videoPath":"/video/认识黑白键2.mp4","childrenPart":null,"name":"认识黑白键2.mp4","pid":4148,"id":4155,"has_source":2,"state":1,"type":102}],"name":"弹奏","pid":4145,"id":4148,"has_source":1,"state":1,"type":1},{"course_id":1,"part_sort":4,"path":"培训用-->第一课","videoPath":"","childrenPart":null,"name":"补充歌唱","pid":4145,"id":4149,"has_source":1,"state":1,"type":1},{"course_id":1,"part_sort":5,"path":"培训用-->第一课","videoPath":"","childrenPart":null,"name":"补充聆听","pid":4145,"id":4150,"has_source":1,"state":1,"type":1},{"course_id":1,"part_sort":6,"path":"培训用-->第一课","videoPath":"","childrenPart":null,"name":"游戏","pid":4145,"id":4151,"has_source":1,"state":1,"type":1}],"name":"第一课","pid":0,"id":4145,"has_source":1,"state":1,"type":0}]
     */

    private int code;
    private String description;
    private List<DetailLoginBean> detail;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DetailLoginBean> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailLoginBean> detail) {
        this.detail = detail;
    }

    public static class DetailLoginBean {
        /**
         * course_id : 1
         * part_sort : 1
         * path : 培训用
         * childrenPart : [{"course_id":1,"part_sort":0,"path":"培训用-->第一课","videoPath":"","childrenPart":[{"course_id":1,"part_sort":0,"path":"培训用-->第一课-->宫商角徴羽","videoPath":"/video/五线谱与高音谱号.mp4","childrenPart":null,"name":"五线谱与高音谱号.mp4","pid":4146,"id":4152,"has_source":2,"state":1,"type":102}],"name":"宫商角徴羽","pid":4145,"id":4146,"has_source":1,"state":1,"type":1},{"course_id":1,"part_sort":1,"path":"培训用-->第一课","videoPath":"","childrenPart":[{"course_id":1,"part_sort":1,"path":"培训用-->第一课-->从耳德，与歌","videoPath":"/video/欢乐颂.mp4","childrenPart":null,"name":"欢乐颂.mp4","pid":4147,"id":4153,"has_source":2,"state":1,"type":102}],"name":"从耳德，与歌","pid":4145,"id":4147,"has_source":1,"state":1,"type":1},{"course_id":1,"part_sort":2,"path":"培训用-->第一课","videoPath":"","childrenPart":[{"course_id":1,"part_sort":2,"path":"培训用-->第一课-->弹奏","videoPath":"/video/认识黑白键1.mp4","childrenPart":null,"name":"认识黑白键1.mp4","pid":4148,"id":4154,"has_source":2,"state":1,"type":102},{"course_id":1,"part_sort":3,"path":"培训用-->第一课-->弹奏","videoPath":"/video/认识黑白键2.mp4","childrenPart":null,"name":"认识黑白键2.mp4","pid":4148,"id":4155,"has_source":2,"state":1,"type":102}],"name":"弹奏","pid":4145,"id":4148,"has_source":1,"state":1,"type":1},{"course_id":1,"part_sort":4,"path":"培训用-->第一课","videoPath":"","childrenPart":null,"name":"补充歌唱","pid":4145,"id":4149,"has_source":1,"state":1,"type":1},{"course_id":1,"part_sort":5,"path":"培训用-->第一课","videoPath":"","childrenPart":null,"name":"补充聆听","pid":4145,"id":4150,"has_source":1,"state":1,"type":1},{"course_id":1,"part_sort":6,"path":"培训用-->第一课","videoPath":"","childrenPart":null,"name":"游戏","pid":4145,"id":4151,"has_source":1,"state":1,"type":1}]
         * name : 第一课
         * pid : 0
         * id : 4145
         * has_source : 1
         * state : 1
         * type : 0
         */

        private int course_id;
        private int part_sort;
        private String path;
        private String name;
        private int pid;
        private int id;
        private int has_source;
        private int state;
        private int type;
        private List<ChildrenPartLoginBeanX> childrenPart;

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public int getPart_sort() {
            return part_sort;
        }

        public void setPart_sort(int part_sort) {
            this.part_sort = part_sort;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getHas_source() {
            return has_source;
        }

        public void setHas_source(int has_source) {
            this.has_source = has_source;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<ChildrenPartLoginBeanX> getChildrenPart() {
            return childrenPart;
        }

        public void setChildrenPart(List<ChildrenPartLoginBeanX> childrenPart) {
            this.childrenPart = childrenPart;
        }

        public static class ChildrenPartLoginBeanX {
            /**
             * course_id : 1
             * part_sort : 0
             * path : 培训用-->第一课
             * videoPath :
             * childrenPart : [{"course_id":1,"part_sort":0,"path":"培训用-->第一课-->宫商角徴羽","videoPath":"/video/五线谱与高音谱号.mp4","childrenPart":null,"name":"五线谱与高音谱号.mp4","pid":4146,"id":4152,"has_source":2,"state":1,"type":102}]
             * name : 宫商角徴羽
             * pid : 4145
             * id : 4146
             * has_source : 1
             * state : 1
             * type : 1
             */

            private int course_id;
            private int part_sort;
            private String path;
            private String videoPath;
            private String name;
            private int pid;
            private int id;
            private int has_source;
            private int state;
            private int type;
            private List<ChildrenPartLoginBean> childrenPart;

            public int getCourse_id() {
                return course_id;
            }

            public void setCourse_id(int course_id) {
                this.course_id = course_id;
            }

            public int getPart_sort() {
                return part_sort;
            }

            public void setPart_sort(int part_sort) {
                this.part_sort = part_sort;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getVideoPath() {
                return videoPath;
            }

            public void setVideoPath(String videoPath) {
                this.videoPath = videoPath;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getHas_source() {
                return has_source;
            }

            public void setHas_source(int has_source) {
                this.has_source = has_source;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public List<ChildrenPartLoginBean> getChildrenPart() {
                return childrenPart;
            }

            public void setChildrenPart(List<ChildrenPartLoginBean> childrenPart) {
                this.childrenPart = childrenPart;
            }

            public static class ChildrenPartLoginBean {
                /**
                 * course_id : 1
                 * part_sort : 0
                 * path : 培训用-->第一课-->宫商角徴羽
                 * videoPath : /video/五线谱与高音谱号.mp4
                 * childrenPart : null
                 * name : 五线谱与高音谱号.mp4
                 * pid : 4146
                 * id : 4152
                 * has_source : 2
                 * state : 1
                 * type : 102
                 */

                private int course_id;
                private int part_sort;
                private String path;
                private String videoPath;
                private Object childrenPart;
                private String name;
                private int pid;
                private int id;
                private int has_source;
                private int state;
                private int type;

                public int getCourse_id() {
                    return course_id;
                }

                public void setCourse_id(int course_id) {
                    this.course_id = course_id;
                }

                public int getPart_sort() {
                    return part_sort;
                }

                public void setPart_sort(int part_sort) {
                    this.part_sort = part_sort;
                }

                public String getPath() {
                    return path;
                }

                public void setPath(String path) {
                    this.path = path;
                }

                public String getVideoPath() {
                    return videoPath;
                }

                public void setVideoPath(String videoPath) {
                    this.videoPath = videoPath;
                }

                public Object getChildrenPart() {
                    return childrenPart;
                }

                public void setChildrenPart(Object childrenPart) {
                    this.childrenPart = childrenPart;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getPid() {
                    return pid;
                }

                public void setPid(int pid) {
                    this.pid = pid;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getHas_source() {
                    return has_source;
                }

                public void setHas_source(int has_source) {
                    this.has_source = has_source;
                }

                public int getState() {
                    return state;
                }

                public void setState(int state) {
                    this.state = state;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }
            }
        }
    }
}
