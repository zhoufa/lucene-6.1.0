package com.zhoufa.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author zhou.fa@diligentfirst.com
 * @create 2016-08-24-11
 */
public class AnalyzerDemo {


    public void whitespaceAnalyzer(String msg) throws IOException {
        WhitespaceAnalyzer whitespaceAnalyzer = new WhitespaceAnalyzer();
        this.getTokens(whitespaceAnalyzer, msg);
    }

    public void simpleAnalyzer(String msg) throws IOException {
        SimpleAnalyzer simpleAnalyzer = new SimpleAnalyzer();
        this.getTokens(simpleAnalyzer, msg);
    }

    public void standardAnalyzer(String msg) throws IOException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        getTokens(analyzer, msg);
    }

    public void stopAnalyzer(String msg) throws IOException {
        StopAnalyzer analyzer = new StopAnalyzer();
        getTokens(analyzer, msg);
    }

    private void getTokens(Analyzer analyzer, String msg) throws IOException {
        TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(msg));
        this.printTokens(analyzer.getClass().getSimpleName(), tokenStream);
    }

    private void printTokens(String analyzerType,TokenStream tokenStream) throws IOException {
        CharTermAttribute ta = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        StringBuilder result =new StringBuilder();
        try {
            while(tokenStream.incrementToken()){
                if(result.length()>0){
                    result.append(",");
                }
                result.append("[").append(ta.toString()).append("]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                tokenStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(analyzerType+"->"+result.toString());
    }
}