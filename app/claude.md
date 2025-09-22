# Project Overview

This project is an Android quiz app designed for practicing German. The app shows an incomplete sentence with a missing word, and the user must choose the correct answer from four multiple-choice options. Each question has only one correct answer.

## Core Features

### Main Menu - Germany Map

The first screen displays a map of Germany with clickable city markers. Each city represents a level and can be clicked to start that level's quiz.

**Clickable Cities:**
- **Hamburg** - Level 1 (Articles)
- **Berlin** - Level 2 (Adjective endings)
- **Leipzig** - Level 3 (Verb conjugation)
- **Düsseldorf** - Level 4 (Word order / modal verbs)
- **Cologne** - Level 5 (Plural forms)
- **Frankfurt** - Level 6 (Prepositions)
- **Stuttgart** - Level 7 (Pronouns)
- **Munich** - Level 8 (Past tense)

**Map Interaction:**
- Each city is represented by a clickable marker/icon on the map
- Completed cities show different visual indicators (e.g., checkmarks, different colors)
- Locked cities (not yet unlocked) are grayed out or show lock icons
- Clicking a city navigates to that level's quiz screen

### Question Display

At the top of the screen, show an incomplete German sentence with a blank (....) where the missing word should be.

**Example:**
```
ich hätte gern ein .... Kaffee
```


### Answer Options

Four buttons (or cards) are shown below the sentence.

**Example options:**
- kleines ✅ (correct)
- zwei
- schon
- bezahlen

### Answer Validation

**If the user selects the correct option:**
- Fill the blank in the sentence
- Show a green checkmark (✔) or a positive animation

**If the user selects the wrong option:**
- Show red feedback (e.g., shake animation or ❌)
- Lose one heart
- reveal the correct answer and go to the next question or game over if no hearts left

### Hearts System

Each level starts with 3 hearts (❤️❤️❤️).

- Wrong answer = Lose one heart
- When all hearts are lost, game over
- Show game over popup with options:
  - Restart level
  - Return to main menu
- hearts will be reset on each level(city)

### Level System

Each level is associated with a German city:

- **Level 1 (Hamburg):** Articles (der, die, das, ein, eine, etc.)
- **Level 2 (Berlin):** Adjective endings
- **Level 3 (Leipzig):** Verb conjugation
- **Level 4 (Düsseldorf):** Word order / modal verbs
- **Level 5 (Cologne):** Plural forms
- **Level 6 (Frankfurt):** Prepositions (with correct case)
- **Level 7 (Stuttgart):** Pronouns (personal / possessive)
- **Level 8 (Munich):** Past tense (Präteritum / Perfekt basics)

## Level Details

### Level 1: Hamburg - Articles
**Example:**
```
ich hätte gern .... Kaffee
```
**Options:** ein ✅, eine, die, der

### Level 2: Berlin - Adjective Endings
**Example:**
```
ich hätte gern ein .... Kaffee
```
**Options:** kleines ✅, kleine, kleiner, kleinen

### Level 3: Leipzig - Verb Conjugation
**Example:**
```
ich .... in Berlin
```
**Options:** wohne ✅, wohnt, wohnst, wohnen

### Level 4: Düsseldorf - Word Order / Modal Verbs
**Example:**
```
ich möchte .... gehen
```
**Options:** ins Kino ✅, im Tisch, der Hund, schnell

### Level 5: Cologne - Plural Forms
**Example:**
```
ich sehe zwei ....
```
**Options:** Autos ✅, Auto, Auton, Autoe

### Level 6: Frankfurt - Prepositions
**Example:**
```
ich gehe .... Schule
```
**Options:** zur ✅, im, beim, durch

### Level 7: Stuttgart - Pronouns
**Example:**
```
das ist .... Buch
```
**Options:** mein ✅, ich, du, mich

### Level 8: Munich - Past Tense
**Example:**
```
gestern .... ich Fußball gespielt
```
**Options:** habe ✅, hat, bin, sein

### Scoring

- Each correct answer = +10 points
- Wrong answers = 0 points (optional: deduct points if needed)
- Show total score at the end of the quiz
- the score displayed on top of screen in left

### Progression

- After answering, move automatically to the next question
- Quiz ends when the last question is completed
- Complete a level to unlock the next city

## Question Flow Logic
The questions count depends on the json file of each city
they should come in random order
after the level successfully completed, the points will be shown with an animation with a continue button. when clicked, player goes to the City Progression activity 


## Question Bank (JSON)

Questions, options, and answers are stored in a JSON file.

**Example format:**
```json
[
  {
    "sentence": "ich hätte gern ein .... Kaffee",
    "options": ["kleines", "zwei", "schon", "bezahlen"],
    "answer": "kleines"
  },
  {
    "sentence": "ich .... in Berlin",
    "options": ["wohne", "isst", "hören", "läuft"],
    "answer": "wohne"
  }
]
```

## Extra Features

### Audio Support
- Use Android TTS (Text-to-Speech) to read the question aloud


### City Progression
- Display city landmarks or backgrounds
- Unlock achievements for completing cities
- Show progress map of Germany

## Tech Stack

- **Platform:** Android Studio
- **Language:** Java or Kotlin
- **UI Components:**
  - ImageView → Germany map background
  - ImageButton/Clickable areas → city markers on map
  - TextView → sentence display
  - Button (or MaterialCardView) → answer options
  - ImageView → heart icons
  - Dialog → game over popup
- **Data Source:** Local JSON file for each city
- **Assets:** Germany map image (JPG format) and city marker icons