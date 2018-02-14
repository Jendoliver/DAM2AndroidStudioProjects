package com.apporelbotna.asgame.model.dao;

import android.content.Context;

import com.apporelbotna.asgame.model.Game;
import com.apporelbotna.asgame.model.Player;
import com.apporelbotna.asgame.model.User;
import com.apporelbotna.asgame.model.dao.search.AbstractSearchTask;
import com.apporelbotna.asgame.model.dao.search.GameSearchTask;
import com.apporelbotna.asgame.model.dao.search.PlayersFromGameSearchTask;
import com.apporelbotna.asgame.model.dao.search.UserGameScoresSearchTask;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jandol on 02/13/2018.
 */

public class StucomGamesDAOImpl implements GameAsyncDAO, AbstractSearchTask.WeakReference
{
    private Context context;

    public StucomGamesDAOImpl(Context context)
    {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }

    private ResultListener resultListener;
    public interface ResultListener
    {
        <T> void onResultReady(T result);
    }

    public void setResultListener(ResultListener resultListener) {
        this.resultListener = resultListener;
    }

    /*----------------------- API RELATED TRANSFER OBJECTS BEGIN --------------------------------*/

    public class StucomGamesTO
    {
        public int error;
        public String message;
    }

    public class UserListTO extends StucomGamesTO
    {
        public UserListItemTO[] data;
    }

    public class GameListTO extends StucomGamesTO implements ConvertableTO<List<Game>>
    {
        public GameTO[] data;

        @Override
        public List<Game> toObject()
        {
            List<Game> games = new ArrayList<>();
            for(GameTO gameTO : data)
                games.add(gameTO.toObject());
            return games;
        }
    }

    public class UserTO extends StucomGamesTO implements ConvertableTO<List<Player>>
    {
        public UserItemTO data;

        @Override
        public List<Player> toObject()
        {
            return data.toPlayerList();
        }
    }

    public class GameRankingTO extends StucomGamesTO implements ConvertableTO<List<Player>>
    {
        public GameItemTO data;

        @Override
        public List<Player> toObject() {
            return data.toPlayersList();
        }
    }

    public class UserListItemTO implements ConvertableTO<User>
    {
        public int id;
        public String username;
        public String firstname;
        public String lastname;
        public String email;
        @SerializedName("avatar_path") public String avatarPath;

        @Override
        public User toObject() {
            return new User(id, username, firstname, lastname, email, avatarPath);
        }
    }

    public class UserItemTO extends UserListItemTO
    {
        public UserRankingTO[] ranking;

        public List<Player> toPlayerList()
        {
            List<Player> players = new ArrayList<>();
            for(UserRankingTO userRankingTO : ranking) {
                Player player = userRankingTO.toObject();
                player.setUser(this.toObject());
                players.add(player);
            }
            return players;
        }
    }

    public class RankingTO implements ConvertableTO<Player> {
        public int id;
        @SerializedName("user_id")
        public int userId;
        @SerializedName("game_id")
        public int gameId;
        public int score;

        @Override
        public Player toObject() { return null; }
    }

    public class UserRankingTO extends RankingTO
    {
        public GameTO game;

        @Override
        public Player toObject()
        {
            Player player = new Player();
            Game playedGame = new Game();
            playedGame.setName(game.name);
            playedGame.setImagePath(game.imagePath);
            player.setGame(playedGame);
            player.setScore(score);
            return player;
        }
    }

    public class GameTO implements ConvertableTO<Game>
    {
        public int id;
        public String name;
        public String description;
        @SerializedName("image_path") public String imagePath;

        @Override
        public Game toObject() {
            return new Game(id, name, description, imagePath);
        }
    }

    public class GameItemTO extends GameTO
    {
        public GameRankingItemTO[] ranking;

        public List<Player> toPlayersList()
        {
            List<Player> players = new ArrayList<>();
            for(GameRankingItemTO rankingItemTO : ranking)
                players.add(rankingItemTO.toObject());
            return players;
        }
    }

    public class GameRankingItemTO extends RankingTO
    {
        public UserListItemTO user;

        @Override
        public Player toObject()
        {
            Player player = new Player();
            Game game = new Game();
            game.setId(gameId);
            player.setUser(user.toObject());
            player.setGame(game);
            player.setName(player.getUser().getUsername()); // CHECK this could be changed in future versions if the same user could have different nicknames in different games
            player.setScore(score);
            return player;
        }
    }

    /*----------------------- API RELATED TRANSFER OBJECTS END --------------------------------*/

    @Override
    public void getPlayer(int id)
    {
        // TODO implement when necessary
    }

    @Override
    public void getGame(int id)
    {
        // TODO implement when necessary
    }

    @Override
    public void findPlayers(Game game)
    {
        PlayersFromGameSearchTask task = new PlayersFromGameSearchTask(this);
        task.execute(game.getId());
    }

    @Override
    public void findGames()
    {
        GameSearchTask task = new GameSearchTask(this);
        task.execute();
    }

    @Override
    public void findScores(User user)
    {
        UserGameScoresSearchTask task = new UserGameScoresSearchTask(this);
        task.execute(user.getId());
    }

    @Override
    public void updateUser(User user) {
        // TODO implement
    }

    @Override
    public void updatePlayer(Player player) {
        // TODO implement in the future
    }

    @Override
    public void updatePlayerScore() {
        // TODO implement in the future
    }

    @Override
    public <Return extends ConvertableTO> void finished(Return callback)
    {
        if(callback != null)
        {
            resultListener.onResultReady(callback.toObject());
        }
    }
}
