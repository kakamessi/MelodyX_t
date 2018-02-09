package com.hzdl.teacher.core;

/**
 * Created by wangshuai on 2017/10/19.
 */

public class NoteInfo {

    //21-108
    private int note;
    //ui上的位置 从1开始计数
    private int noteIndex;
    private int keyIndex;
    //显示颜色
    private boolean idNoteRed;
    //音符是否被弹奏过
    private boolean isUsed;

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    //命中次数
    private int hitCount = -1;
    //弹奏  低音
    private NoteInfo info;

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public NoteInfo getInfo() {
        return info;
    }

    public void setInfo(NoteInfo info) {
        this.info = info;
    }

    public NoteInfo() {
    }

    public NoteInfo(int note, int noteIndex, int keyIndex, boolean idNoteRed) {
        this.note = note;
        this.noteIndex = noteIndex;
        this.keyIndex = keyIndex;
        this.idNoteRed = idNoteRed;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getNoteIndex() {
        return noteIndex;
    }

    public void setNoteIndex(int noteIndex) {
        this.noteIndex = noteIndex;
    }

    public int getKeyIndex() {
        return keyIndex;
    }

    public void setKeyIndex(int keyIndex) {
        this.keyIndex = keyIndex;
    }

    public boolean isIdNoteRed() {
        return idNoteRed;
    }

    public void setIdNoteRed(boolean idNoteRed) {
        this.idNoteRed = idNoteRed;
    }

    //复位
    public void reSet(){
        setUsed(false);
        if(getInfo()!=null){
            getInfo().setUsed(false);
        }
    }

    //当前音符环节是否结束
    public boolean isFinish(){
        boolean result = true;

        if(getInfo()!=null && !getInfo().isUsed()){
            result = false;
        }

        if(!isUsed()){
            result = false;
        }

        return result;
    }

    //设置已用状态
    public void setUsedStatu(int note){
        if(getInfo()!=null && getInfo().getNote()== note){
            getInfo().setUsed(true);
        }
        if(getNote() == note){
            setUsed(true);
        }
    }

    //获取对应音符信息
    public NoteInfo getNoteByid(int note){
        if(getInfo()!=null && getInfo().getNote()==note){
            return getInfo();
        }
        if(getNote() == note){
            return this;
        }
        return null;
    }

    //检测对应音符是否被 弹奏过
    public boolean checkUsed(int note){
        boolean result = false;

        if(getNoteByid(note)!=null && getNoteByid(note).isUsed()){
            result = true;
        }

        return result;
    }

}

