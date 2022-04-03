package com.werun.onlinenote_backend.service;

import com.werun.onlinenote_backend.bean.CategoryBean;
import com.werun.onlinenote_backend.bean.NoteBean;
import com.werun.onlinenote_backend.dao.CategoryDao;
import com.werun.onlinenote_backend.dao.NoteDao;
import com.werun.onlinenote_backend.entity.Category;
import com.werun.onlinenote_backend.entity.Note;
import com.werun.onlinenote_backend.entity.User;
import com.werun.onlinenote_backend.result.NoteResult;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @ClassName NoteService
 * @Description 实体Note的service层
 * @Author liuzijun
 * @Updater
 * @Create 2022-03-31
 * @Update
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NoteService {

    private final NoteDao noteDao;
    private final CategoryDao categoryDao;

    public NoteResult addNote(String noteTitle, String cid, String noteContent) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        Category category = categoryDao.findCategoryByCidAndUser(cid, user);

        if(category == null) {
            return new NoteResult(false, "Category did not exist");
        }

        Note note = new Note();

        note.setNoteTitle(noteTitle);
        note.setNoteCategory(category);
        note.setNoteContent(noteContent);
        note.setNoteCreateTime(new Timestamp(new Date().getTime()));
        note.setUid(user.getUid());

        noteDao.save(note);
        return new NoteResult(new NoteBean(note), new CategoryBean(category));
    }

    public NoteResult deleteNote(String nid) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        Note note = noteDao.findNoteByNidAndUid(nid, user.getUid());

        if(note == null) {
            return new NoteResult(false, "This didn't exist");
        }

        noteDao.delete(note);
        return new NoteResult(true, "Delete Successfully");
    }

    public NoteResult getNote(String nid) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        Note note = noteDao.findNoteByNidAndUid(nid, user.getUid());

        if(note == null) {
            return new NoteResult(false, "This didn't exist");
        }

        Category category = note.getNoteCategory();

        return new NoteResult(new NoteBean(note), new CategoryBean(category));
    }

    public NoteResult changeNoteTitle(String nid, String changeNoteTitle) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        Note note = noteDao.findNoteByNidAndUid(nid, user.getUid());

        if(note == null) {
            return new NoteResult(false, "This didn't exist");
        }

        note.setNoteTitle(changeNoteTitle);
        noteDao.save(note);

        Category category = note.getNoteCategory();

        return new NoteResult(new NoteBean(note), new CategoryBean(category));
    }

    public NoteResult changeNoteCategory(String nid, String cid) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        Note note = noteDao.findNoteByNidAndUid(nid, user.getUid());

        Category category = categoryDao.findCategoryByCidAndUser(cid, user);

        if(note == null) {
            return new NoteResult(false, "This didn't exist");
        }

        note.setNoteCategory(category);
        noteDao.save(note);

        return new NoteResult(new NoteBean(note), new CategoryBean(category));
    }

    public NoteResult changeNoteCompletedState(String nid, Boolean changeNoteCompletedState) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        Note note = noteDao.findNoteByNidAndUid(nid, user.getUid());

        if(note == null) {
            return new NoteResult(false, "This didn't exist");
        }

        note.setNoteCompletedState(changeNoteCompletedState);
        noteDao.save(note);

        Category category = note.getNoteCategory();

        return new NoteResult(new NoteBean(note), new CategoryBean(category));
    }

    public NoteResult changeNoteContent(String nid, String changeNoteContent) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        Note note = noteDao.findNoteByNidAndUid(nid, user.getUid());

        if(note == null) {
            return new NoteResult(false, "This didn't exist");
        }

        note.setNoteContent(changeNoteContent);
        noteDao.save(note);

        Category category = note.getNoteCategory();

        return new NoteResult(new NoteBean(note), new CategoryBean(category));
    }
}
