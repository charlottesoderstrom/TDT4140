-- LAGER DATABASEN START

CREATE TABLE IF NOT EXISTS Ingredients (
    IngredientName VARCHAR(40) NOT NULL UNIQUE,
	CONSTRAINT Ingredient_PK PRIMARY KEY (IngredientName)
);

CREATE TABLE IF NOT EXISTS Recipes (
	RecipeID INTEGER NOT NULL auto_increment,
    Title VARCHAR(40) UNIQUE,
    Instructions VARCHAR (5000),
    Verified INTEGER,
	CONSTRAINT Recipe_PK PRIMARY KEY (RecipeID)
);

CREATE TABLE IF NOT EXISTS IngredientsInRecipes (
	RecipeID INTEGER NOT NULL,
    IngredientName VARCHAR(40),
    Quantity VARCHAR(40),
	CONSTRAINT RIR_PK PRIMARY KEY (RecipeID, IngredientName),
    CONSTRAINT IngredientToList_FK1 FOREIGN KEY (IngredientName) REFERENCES Ingredients(IngredientName)
																ON UPDATE CASCADE
                                                                ON DELETE CASCADE,
    CONSTRAINT RecipeToList_FK2 FOREIGN KEY (RecipeID) REFERENCES Recipes(RecipeID)
																ON UPDATE CASCADE
                                                                ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Users (
UserID INTEGER NOT NULL auto_increment,
UserName VARCHAR(50) NOT NULL UNIQUE,
Role VARCHAR(40) NOT NULL,
CONSTRAINT User_PK PRIMARY KEY (UserID, UserName)
);
-- LAGER DATABASEN SLUTT


-- FYLLER DATABASEN MED EKSEMPELDATA

INSERT INTO Recipes (Title, Instructions, Verified ) VALUES ("Tomato Soup", "Mash tomatoes. Put in Crockpot. Cook for a couple of minutes. Add water, and season to taste. Serve warm.", 0);
INSERT INTO Recipes (Title, Instructions, Verified ) VALUES ("Pancakes", "Make batter. Butter pan. Cook in pan. Flip onto plate. Serve with bacon.", 0);
INSERT INTO Recipes (Title, Instructions, Verified ) VALUES ("Apple Pie", "For the pastry: 
1) To make the pastry by hand, in a medium bowl, whisk together the flour, sugar and salt. Using your fingers, work the butter into the dry ingredients until it resembles yellow corn meal mixed with bean sized bits of butter. (If the flour/butter mixture gets warm, refrigerate it for 10 mins before proceeding.) Add the egg and stir the dough together with a fork or by hand in the bowl. If the dough is dry, sprinkle up to a tbsp more of cold water over the mixture.
2) To make the pastry in a food processor, with the machine fitted with the metal blade, pulse the flour, sugar and salt until combined. Add the butter and pulse until it resembles yellow corn meal mixed with bean size bits of butter, about 10 times. Add the egg and pulse 1 to 2 times; don't let the dough form into a ball in the machine. (If the dough is very dry add up to a tbsp more of cold water.) Remove the bowl from the machine, remove the blade, and bring the dough together by hand.
3) Form the dough into a disk, wrap in clingfilm and refrigerate until thoroughly chilled, at least 1 hr.

To make the filling: 
1) Put the lemon juice in a medium bowl. Peel, halve, and core the apples. Cut each half into 4 wedges. Toss the apple with the lemon juice. Add the sugar and toss to combine evenly.
2) In a large skillet, melt the butter over a medium-high heat. Add the apples, and cook, stirring, until the sugar dissolves and the mixture begins to simmer, about 2 mins. Cover, reduce the heat to medium-low, and cook until the apples soften and release most of their juices, about 7 mins.
3) Strain the apples in a colander over a medium bowl to catch all the juice. Shake the colander to get as much liquid as possible. Return the juices to the skillet, and simmer over a medium heat until thickened and lightly caramelized, about 10 mins.
4) In a medium bowl, toss the apples with the reduced juice and spices. Set aside to cool completely. (This filling can be made up to 2 days ahead and refrigerated or frozen for up to 6 months.)

To make the pie: 
1) Cut the dough in half. On a lightly floured surface, roll each half of dough into a disc about 28 to 30cm wide. Layer the dough between pieces of parchment or wax paper on a baking sheet, and refrigerate for at least 10 mins. Place a rack in the lower third of the oven and preheat the oven to 190C/Gas 5.
2) Line the bottom of a 23cm pie pan with one of the discs of dough, and trim it so it lays about 1cm beyond the edge of the pan. Put the apple filling in the pan and mound it slightly in the centre. Brush the top edges of the dough with the egg.
3) Place the second disc of dough over the top. Fold the top layer of dough under the edge of the bottom layer and press the edges together to form a seal. Flute the edge as desired. Brush the surface of the dough with egg and then sprinkle with sugar. Pierce the top of the dough in several places to allow steam to escape while baking. Refrigerate for at least 15 mins.
4) Bake the pie on a baking sheet until the crust is golden, about 50 mins. Cool on a rack before serving. The pie keeps well at room temperature (covered) for 24 hours, or refrigerated for up to 4 days.
Cook's Note: You may freeze the uncooked pie, but don't brush it with egg or dust it with sugar beforehand. Place the pie in the freezer for 30 mins, to harden it slightly, and then double wrap it with cling film. Freeze for up to 6 months. When ready to bake, unwrap the pie and brush it with egg and sprinkle with sugar. Bake, from the frozen state, until golden brown, about 1 hr and 10 mins.", 1);
INSERT INTO Recipes (Title, Instructions, Verified ) VALUES ("Croissant", "Make dough:
Stir together warm milk, brown sugar, and yeast in bowl of standing mixer and let stand until foamy, about 5 minutes. (If it doesn’t foam, discard and start over.) Add 3 3/4 cups flour and salt and mix with dough hook at low speed until dough is smooth and very soft, about 7 minutes.
Transfer dough to a work surface and knead by hand 2 minutes, adding more flour as necessary, a little at a time, to make a soft, slightly sticky dough. Form dough into a roughly 1 1/2-inch-thick rectangle and chill, wrapped in plastic wrap, until cold, about 1 hour.
Prepare and shape butter:
After dough has chilled, arrange sticks of butter horizontally, their sides touching, on a work surface. Pound butter with a rolling pin to soften slightly (butter should be malleable but still cold). Scrape butter into a block and put on a kitchen towel, then cover with other towel. Pound and roll out on both sides until butter forms a uniform 8- by 5-inch rectangle. Chill, wrapped in towels, while rolling out dough.
Roll out dough:
Unwrap dough and roll out on a lightly floured surface, dusting with flour as necessary and lifting and stretching dough (especially in corners), into a 16- by 10-inch rectangle. Arrange dough with a short side nearest you. Put butter in center of dough so that long sides of butter are parallel to short sides of dough. Fold as you would a letter: bottom third of dough over butter, then top third down over dough. Brush off excess flour with pastry brush.
Roll out dough:
Turn dough so a short side is nearest you, then flatten dough slightly by pressing down horizontally with rolling pin across dough at regular intervals, making uniform impressions. Roll out dough into a 15- by 10-inch rectangle, rolling just to but not over ends.
Brush off any excess flour. Fold in thirds like a letter, as above, stretching corners to square off dough, forming a 10- by 5-inch rectangle. (You have completed the first fold.) Chill, wrapped in plastic wrap, 1 hour.
Make remaining folds:
Make 3 more folds in same manner, chilling dough 1 hour after each fold, for a total of 4 folds. (If any butter oozes out while rolling, sprinkle with flour to prevent sticking.) Wrap dough tightly in plastic wrap and chill at least 8 hours but no more than 18 (after 18 hours, dough may not rise sufficiently when baked).", 1);
-- INSERT INTO Recipes (Title, Instructions ) VALUES ("Omelette", "Crack eggs. Season with salt and pepper. Add sausage, paprika and cheese. Cook for a couple of minutes.");

INSERT INTO Ingredients VALUES ("Tomato");
INSERT INTO Ingredients VALUES ("Salt");
INSERT INTO Ingredients VALUES ("Water");
INSERT INTO Ingredients VALUES ("Flour");
INSERT INTO Ingredients VALUES ("Butter");
INSERT INTO Ingredients VALUES ("Milk");
INSERT INTO Ingredients VALUES ("Apple");
INSERT INTO Ingredients VALUES ("Sugar");
INSERT INTO Ingredients VALUES ("Egg");
INSERT INTO Ingredients VALUES ("Pepper");
INSERT INTO Ingredients VALUES ("Sausage");
INSERT INTO Ingredients VALUES ("Paprika");
INSERT INTO IngredientsInRecipes VALUES(1, "Tomato", "5");
INSERT INTO IngredientsInRecipes VALUES(1, "Water", "0.5 litres");
INSERT INTO IngredientsInRecipes VALUES(1, "Salt", "2 Table spoons");
INSERT INTO IngredientsInRecipes VALUES(1, "Pepper", "2 Table spoons");
INSERT INTO IngredientsInRecipes VALUES(2, "Flour", "600 grams");
INSERT INTO IngredientsInRecipes VALUES(2, "Water", "0.3 litres");
INSERT INTO IngredientsInRecipes VALUES(2, "Salt", "A table spoon");
INSERT INTO IngredientsInRecipes VALUES(2, "Sugar", "250 grams");
INSERT INTO IngredientsInRecipes VALUES(2, "Egg", "3");

INSERT INTO IngredientsInRecipes VALUES(3, "Flour", "350 grams");
INSERT INTO IngredientsInRecipes VALUES(3, "Sugar", "20 grams");
INSERT INTO IngredientsInRecipes VALUES(3, "Salt", "1/4 tea spoon");
INSERT INTO IngredientsInRecipes VALUES(3, "Butter", "200 grams diced");
INSERT INTO IngredientsInRecipes VALUES(3, "Egg", "1 large");
INSERT INTO IngredientsInRecipes VALUES(3, "Apple", "3");
INSERT INTO IngredientsInRecipes VALUES(4, "Milk", "1 1/2 cups");
INSERT INTO IngredientsInRecipes VALUES(4, "Sugar", "1/4 cups");
INSERT INTO IngredientsInRecipes VALUES(4, "Flour", "4 cups");
INSERT INTO IngredientsInRecipes VALUES(4, "Salt", "1 tea spoon");
INSERT INTO IngredientsInRecipes VALUES(4, "Butter", "3 sticks");
-- INSERT INTO IngredientsInRecipes VALUES(3, "Egg", "4");
-- INSERT INTO IngredientsInRecipes VALUES(3, "Salt", "2 Table spoons");
-- INSERT INTO IngredientsInRecipes VALUES(3, "Pepper", "2 Table spoons");
-- INSERT INTO IngredientsInRecipes VALUES(3, "Sausage", "3");
-- INSERT INTO IngredientsInRecipes VALUES(3, "Paprika", "1 red and 1 green");
INSERT INTO Users (UserName, Role) VALUES("admin", "admin");
INSERT INTO Users (UserName, Role) VALUES("bob", "regularUser");
INSERT INTO Users (UserName, Role) VALUES("mrChef", "chef");
INSERT INTO Users (UserName, Role) VALUES("mrGuest", "guest");



-- DIVERSE SPØRRINGER

SELECT * FROM Recipes;
SELECT * FROM Ingredients;
SELECT * FROM IngredientsInRecipes;

SELECT *
	FROM (Recipes Natural Join IngredientsInRecipes) Natural Join Ingredients;
    
SELECT RecipeID, Title, Instructions, IngredientName, Quantity 
	FROM (Recipes Natural Join IngredientsInRecipes) Natural Join Ingredients
    ORDER BY Title ASC;
    
SELECT RecipeID, Title, Instructions, IngredientName, Quantity 
	FROM (Recipes Natural Join IngredientsInRecipes) Natural Join Ingredients
    WHERE Recipes.Title = "Omelette";
    
SELECT RecipeID, Title, Instructions, IngredientName, Quantity 
	FROM (Recipes Natural Join IngredientsInRecipes) Natural Join Ingredients
    WHERE Ingredients.IngredientName = "Water";

SELECT RecipeID, Title, Instructions, IngredientName, Quantity 
	FROM (Recipes Natural Join IngredientsInRecipes) Natural Join Ingredients
    WHERE Recipes.RecipeID = 1;

SELECT RecipeID, Title, Instructions, Verified, IngredientName, Quantity
                    FROM (Recipes Natural Join IngredientsInRecipes) Natural Join Ingredients
                    WHERE Verified = '1';
