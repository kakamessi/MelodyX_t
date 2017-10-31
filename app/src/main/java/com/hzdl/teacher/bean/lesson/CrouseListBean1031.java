package com.hzdl.teacher.bean.lesson;

import java.util.List;

/**
 * Created by wangshuai on 2017/10/25.
 */

public class CrouseListBean1031 {


    /**
     * code : 200
     * description : 请求成功
     * detail : [{"course_id":1,"part_sort":1,"path":"一年级","childrenPart":[{"course_id":1,"part_sort":1,"is_light":0,"pid":1,"has_source":1,"type":1,"is_screen":0,"path":"一年级-->第一课","childrenPart":[{"course_id":1,"part_sort":1,"is_light":0,"pid":2,"has_source":2,"type":102,"is_screen":0,"path":"一年级-->第一课-->宫商角徴羽","childrenPart":null,"name":"五线谱与高音谱号1","id":3,"state":1,"sourceName":"1-1-1-1.mp4","sourcePath":"/video/五线谱与高音谱号.mp4"},{"course_id":1,"part_sort":2,"is_light":0,"pid":2,"has_source":2,"type":102,"is_screen":0,"path":"一年级-->第一课-->宫商角徴羽","childrenPart":null,"name":"五线谱与高音谱号2","id":4,"state":1,"sourceName":"1-1-1-2.mp4","sourcePath":"/video/五线谱与高音谱号2.mp4"}],"name":"宫商角徴羽","id":2,"state":1,"sourceName":"","sourcePath":""},{"course_id":1,"part_sort":3,"is_light":0,"pid":1,"has_source":1,"type":1,"is_screen":0,"path":"一年级-->第一课","childrenPart":[{"course_id":1,"part_sort":3,"is_light":0,"pid":5,"has_source":2,"type":102,"is_screen":0,"path":"一年级-->第一课-->从耳德，与歌","childrenPart":null,"name":"欢乐颂","id":6,"state":1,"sourceName":"1-1-2-1.mp4","sourcePath":"/video/欢乐颂.mp4"}],"name":"从耳德，与歌","id":5,"state":1,"sourceName":"","sourcePath":""},{"course_id":1,"part_sort":4,"is_light":0,"pid":1,"has_source":1,"type":1,"is_screen":0,"path":"一年级-->第一课","childrenPart":[{"course_id":1,"part_sort":4,"is_light":0,"pid":7,"has_source":2,"type":102,"is_screen":0,"path":"一年级-->第一课-->弹奏","childrenPart":null,"name":"认识黑白键1","id":8,"state":1,"sourceName":"1-1-3-1.mp4","sourcePath":"/video/认识黑白键1.mp4"},{"course_id":1,"part_sort":5,"is_light":0,"pid":7,"has_source":2,"type":102,"is_screen":0,"path":"一年级-->第一课-->弹奏","childrenPart":null,"name":"认识黑白键2","id":9,"state":1,"sourceName":"1-1-3-2.mp4","sourcePath":"/video/认识黑白键2.mp4"},{"course_id":1,"part_sort":6,"is_light":0,"pid":7,"has_source":2,"type":102,"is_screen":0,"path":"一年级-->第一课-->弹奏","childrenPart":null,"name":"练习视频1","id":10,"state":1,"sourceName":"1-1-3-3.mp4","sourcePath":"/video/1-1-3-3.mp4"},{"course_id":1,"part_sort":7,"is_light":0,"pid":7,"has_source":2,"type":103,"is_screen":1,"path":"一年级-->第一课-->弹奏","childrenPart":null,"name":"跟灯练习","id":11,"state":1,"sourceName":"1-1-3-4.png","sourcePath":"/video/1-1-3-4.png"},{"course_id":1,"part_sort":8,"is_light":1,"pid":7,"has_source":2,"type":102,"is_screen":1,"path":"一年级-->第一课-->弹奏","childrenPart":null,"name":"练习视频2","id":12,"state":1,"sourceName":"1-1-3-5.mp4","sourcePath":"/video/1-1-3-5.mp4"}],"name":"弹奏","id":7,"state":1,"sourceName":"","sourcePath":""},{"course_id":1,"part_sort":9,"is_light":0,"pid":1,"has_source":1,"type":1,"is_screen":0,"path":"一年级-->第一课","childrenPart":null,"name":"补充歌唱","id":13,"state":1,"sourceName":"","sourcePath":""},{"course_id":1,"part_sort":10,"is_light":0,"pid":1,"has_source":1,"type":1,"is_screen":0,"path":"一年级-->第一课","childrenPart":null,"name":"补充聆听","id":14,"state":1,"sourceName":"","sourcePath":""},{"course_id":1,"part_sort":11,"is_light":0,"pid":1,"has_source":1,"type":1,"is_screen":0,"path":"一年级-->第一课","childrenPart":null,"name":"游戏","id":15,"state":1,"sourceName":"","sourcePath":""}],"is_light":0,"name":"第一课","pid":0,"id":1,"has_source":1,"state":1,"type":0,"is_screen":0}]
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
         * path : 一年级
         * childrenPart : [{"course_id":1,"part_sort":1,"is_light":0,"pid":1,"has_source":1,"type":1,"is_screen":0,"path":"一年级-->第一课","childrenPart":[{"course_id":1,"part_sort":1,"is_light":0,"pid":2,"has_source":2,"type":102,"is_screen":0,"path":"一年级-->第一课-->宫商角徴羽","childrenPart":null,"name":"五线谱与高音谱号1","id":3,"state":1,"sourceName":"1-1-1-1.mp4","sourcePath":"/video/五线谱与高音谱号.mp4"},{"course_id":1,"part_sort":2,"is_light":0,"pid":2,"has_source":2,"type":102,"is_screen":0,"path":"一年级-->第一课-->宫商角徴羽","childrenPart":null,"name":"五线谱与高音谱号2","id":4,"state":1,"sourceName":"1-1-1-2.mp4","sourcePath":"/video/五线谱与高音谱号2.mp4"}],"name":"宫商角徴羽","id":2,"state":1,"sourceName":"","sourcePath":""},{"course_id":1,"part_sort":3,"is_light":0,"pid":1,"has_source":1,"type":1,"is_screen":0,"path":"一年级-->第一课","childrenPart":[{"course_id":1,"part_sort":3,"is_light":0,"pid":5,"has_source":2,"type":102,"is_screen":0,"path":"一年级-->第一课-->从耳德，与歌","childrenPart":null,"name":"欢乐颂","id":6,"state":1,"sourceName":"1-1-2-1.mp4","sourcePath":"/video/欢乐颂.mp4"}],"name":"从耳德，与歌","id":5,"state":1,"sourceName":"","sourcePath":""},{"course_id":1,"part_sort":4,"is_light":0,"pid":1,"has_source":1,"type":1,"is_screen":0,"path":"一年级-->第一课","childrenPart":[{"course_id":1,"part_sort":4,"is_light":0,"pid":7,"has_source":2,"type":102,"is_screen":0,"path":"一年级-->第一课-->弹奏","childrenPart":null,"name":"认识黑白键1","id":8,"state":1,"sourceName":"1-1-3-1.mp4","sourcePath":"/video/认识黑白键1.mp4"},{"course_id":1,"part_sort":5,"is_light":0,"pid":7,"has_source":2,"type":102,"is_screen":0,"path":"一年级-->第一课-->弹奏","childrenPart":null,"name":"认识黑白键2","id":9,"state":1,"sourceName":"1-1-3-2.mp4","sourcePath":"/video/认识黑白键2.mp4"},{"course_id":1,"part_sort":6,"is_light":0,"pid":7,"has_source":2,"type":102,"is_screen":0,"path":"一年级-->第一课-->弹奏","childrenPart":null,"name":"练习视频1","id":10,"state":1,"sourceName":"1-1-3-3.mp4","sourcePath":"/video/1-1-3-3.mp4"},{"course_id":1,"part_sort":7,"is_light":0,"pid":7,"has_source":2,"type":103,"is_screen":1,"path":"一年级-->第一课-->弹奏","childrenPart":null,"name":"跟灯练习","id":11,"state":1,"sourceName":"1-1-3-4.png","sourcePath":"/video/1-1-3-4.png"},{"course_id":1,"part_sort":8,"is_light":1,"pid":7,"has_source":2,"type":102,"is_screen":1,"path":"一年级-->第一课-->弹奏","childrenPart":null,"name":"练习视频2","id":12,"state":1,"sourceName":"1-1-3-5.mp4","sourcePath":"/video/1-1-3-5.mp4"}],"name":"弹奏","id":7,"state":1,"sourceName":"","sourcePath":""},{"course_id":1,"part_sort":9,"is_light":0,"pid":1,"has_source":1,"type":1,"is_screen":0,"path":"一年级-->第一课","childrenPart":null,"name":"补充歌唱","id":13,"state":1,"sourceName":"","sourcePath":""},{"course_id":1,"part_sort":10,"is_light":0,"pid":1,"has_source":1,"type":1,"is_screen":0,"path":"一年级-->第一课","childrenPart":null,"name":"补充聆听","id":14,"state":1,"sourceName":"","sourcePath":""},{"course_id":1,"part_sort":11,"is_light":0,"pid":1,"has_source":1,"type":1,"is_screen":0,"path":"一年级-->第一课","childrenPart":null,"name":"游戏","id":15,"state":1,"sourceName":"","sourcePath":""}]
         * is_light : 0
         * name : 第一课
         * pid : 0
         * id : 1
         * has_source : 1
         * state : 1
         * type : 0
         * is_screen : 0
         */

        private int course_id;
        private int part_sort;
        private String path;
        private int is_light;
        private String name;
        private int pid;
        private int id;
        private int has_source;
        private int state;
        private int type;
        private int is_screen;
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

        public int getIs_light() {
            return is_light;
        }

        public void setIs_light(int is_light) {
            this.is_light = is_light;
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

        public int getIs_screen() {
            return is_screen;
        }

        public void setIs_screen(int is_screen) {
            this.is_screen = is_screen;
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
             * part_sort : 1
             * is_light : 0
             * pid : 1
             * has_source : 1
             * type : 1
             * is_screen : 0
             * path : 一年级-->第一课
             * childrenPart : [{"course_id":1,"part_sort":1,"is_light":0,"pid":2,"has_source":2,"type":102,"is_screen":0,"path":"一年级-->第一课-->宫商角徴羽","childrenPart":null,"name":"五线谱与高音谱号1","id":3,"state":1,"sourceName":"1-1-1-1.mp4","sourcePath":"/video/五线谱与高音谱号.mp4"},{"course_id":1,"part_sort":2,"is_light":0,"pid":2,"has_source":2,"type":102,"is_screen":0,"path":"一年级-->第一课-->宫商角徴羽","childrenPart":null,"name":"五线谱与高音谱号2","id":4,"state":1,"sourceName":"1-1-1-2.mp4","sourcePath":"/video/五线谱与高音谱号2.mp4"}]
             * name : 宫商角徴羽
             * id : 2
             * state : 1
             * sourceName :
             * sourcePath :
             */

            private int course_id;
            private int part_sort;
            private int is_light;
            private int pid;
            private int has_source;
            private int type;
            private int is_screen;
            private String path;
            private String name;
            private int id;
            private int state;
            private String sourceName;
            private String sourcePath;
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

            public int getIs_light() {
                return is_light;
            }

            public void setIs_light(int is_light) {
                this.is_light = is_light;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public int getHas_source() {
                return has_source;
            }

            public void setHas_source(int has_source) {
                this.has_source = has_source;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getIs_screen() {
                return is_screen;
            }

            public void setIs_screen(int is_screen) {
                this.is_screen = is_screen;
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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getSourceName() {
                return sourceName;
            }

            public void setSourceName(String sourceName) {
                this.sourceName = sourceName;
            }

            public String getSourcePath() {
                return sourcePath;
            }

            public void setSourcePath(String sourcePath) {
                this.sourcePath = sourcePath;
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
                 * part_sort : 1
                 * is_light : 0
                 * pid : 2
                 * has_source : 2
                 * type : 102
                 * is_screen : 0
                 * path : 一年级-->第一课-->宫商角徴羽
                 * childrenPart : null
                 * name : 五线谱与高音谱号1
                 * id : 3
                 * state : 1
                 * sourceName : 1-1-1-1.mp4
                 * sourcePath : /video/五线谱与高音谱号.mp4
                 */

                private int course_id;
                private int part_sort;
                private int is_light;
                private int pid;
                private int has_source;
                private int type;
                private int is_screen;
                private String path;
                private Object childrenPart;
                private String name;
                private int id;
                private int state;
                private String sourceName;
                private String sourcePath;

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

                public int getIs_light() {
                    return is_light;
                }

                public void setIs_light(int is_light) {
                    this.is_light = is_light;
                }

                public int getPid() {
                    return pid;
                }

                public void setPid(int pid) {
                    this.pid = pid;
                }

                public int getHas_source() {
                    return has_source;
                }

                public void setHas_source(int has_source) {
                    this.has_source = has_source;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getIs_screen() {
                    return is_screen;
                }

                public void setIs_screen(int is_screen) {
                    this.is_screen = is_screen;
                }

                public String getPath() {
                    return path;
                }

                public void setPath(String path) {
                    this.path = path;
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

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getState() {
                    return state;
                }

                public void setState(int state) {
                    this.state = state;
                }

                public String getSourceName() {
                    return sourceName;
                }

                public void setSourceName(String sourceName) {
                    this.sourceName = sourceName;
                }

                public String getSourcePath() {
                    return sourcePath;
                }

                public void setSourcePath(String sourcePath) {
                    this.sourcePath = sourcePath;
                }
            }
        }
    }
}
