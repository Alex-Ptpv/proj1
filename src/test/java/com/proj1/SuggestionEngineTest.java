package com.proj1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class SuggestionEngineTest {
    private SuggestionEngine suggestionEngine = new SuggestionEngine();

    @Mock
    private SuggestionsDatabase mockSuggestionDB;
    private boolean testInstanceSame = false;

    @Test
    public void testGenerateSuggestions() throws Exception {
        suggestionEngine.loadDictionaryData( Paths.get( ClassLoader.getSystemResource("words.txt").getPath()));

//        Assertions.assertTrue(testInstanceSame);
        Assertions.assertTrue(suggestionEngine.generateSuggestions("hellw").contains("hello"));
    }

    @Test
    public void testGenerateSuggestionsFail() throws Exception {
        suggestionEngine.loadDictionaryData( Paths.get( ClassLoader.getSystemResource("words.txt").getPath()));

        testInstanceSame = true;
        Assertions.assertTrue(testInstanceSame);
        Assertions.assertFalse(suggestionEngine.generateSuggestions("hello").contains("hello"));
    }

    @Test
    public void testSuggestionsAsMock() {
        Map<String,Integer> wordMapForTest = new HashMap<>();

        wordMapForTest.put("test", 1);

        Mockito.when(mockSuggestionDB.getWordMap()).thenReturn(wordMapForTest);

        suggestionEngine.setWordSuggestionDB(mockSuggestionDB);

        Assertions.assertFalse(suggestionEngine.generateSuggestions("test").contains("test"));

        Assertions.assertTrue(suggestionEngine.generateSuggestions("tes").contains("test"));
    }

    @Test
    public void testGenerateSuggestionsEmptyDictionary() throws Exception {
        String result = suggestionEngine.generateSuggestions("hello");
        Assertions.assertFalse(result.contains("hello"));
    }

    @Test
    public void testGenerateSuggestionsWithSpecialCharacters() throws Exception {
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").getPath()));

        String result = suggestionEngine.generateSuggestions("h@llo");
        Assertions.assertTrue(result.contains("hello"));
    }

    @Test
    public void testGenerateSuggestionsWithNumbers() throws Exception {
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").getPath()));

        String result = suggestionEngine.generateSuggestions("h32llo");
        Assertions.assertTrue(result.contains("hello"));
    }
}