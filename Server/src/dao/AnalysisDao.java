package dao;

import entity.Analysis;

public interface AnalysisDao {
    Object getAnalysisObject();
    boolean addAnalysisResults (Analysis analysis);
}
