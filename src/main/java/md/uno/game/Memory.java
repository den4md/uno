package md.uno.game;

import md.uno.game.models.Player;
import md.uno.game.models.Table;

import java.util.ArrayList;

public class Memory
{
    private static final Memory instance = new Memory();

    private Memory()
    {
    }

    public static Memory getInstance()
    {
        return instance;
    }

    private final ArrayList<Table> tables = new ArrayList<>();

    private final ArrayList<Player> lobby = new ArrayList<>();

    private final ArrayList<Player> readyToPlay = new ArrayList<>();

    public ArrayList<Table> getTables()
    {
        return tables;
    }

    public int getMaxPLayers()
    {
        return 4;   // for one table
    }

    public int getMinPLayers()
    {
        return 2;   // for one table
    }

    public ArrayList<Player> getLobby()
    {
        return lobby;
    }

    public ArrayList<Player> getReadyToPlay()
    {
        return readyToPlay;
    }

    private ArrayList<Player> getAllPlayers()
    {
        ArrayList<Player> result = new ArrayList<>(lobby);
        result.addAll(readyToPlay);
        for (Table table : tables)
        {
            result.addAll(table.getPlayers());
        }
        return result;
    }

    public Player findPlayerByJsession(String jsession)
    {
        if (jsession == null)
        {
            return null;
        }

        for (Player player : getAllPlayers())
        {
            if (player.getJsession().equals(jsession))
            {
                return player;
            }
        }

        return null;
    }

    public Player findPlayerByLogin(String login)
    {
        if (login == null)
        {
            return null;
        }

        for (Player player : getAllPlayers())
        {
            if (player.getLogin().equals(login))
            {
                return player;
            }
        }

        return null;
    }

    private Table addTable()
    {
        Table table = new Table(getMaxPLayers());
        tables.add(table);
        return table;
    }

    private Table addTable(ArrayList<Player> players) throws Exception
    {
        Table table = addTable();
//        for (Player player : players)
//        {
//            table.addPlayer(player);
//        }
        table.addPlayers(players);
        return table;
    }

    public void removeTable(Table table)
    {
        tables.remove(table);
    }

    public boolean isPlayerInLobby(Player player)
    {
        return lobby.contains(player);
    }

    public boolean isPlayerReadyToPlay(Player player)
    {
        return readyToPlay.contains(player);
    }

    public boolean isPlayerInGame(Player player)
    {
        return !((isPlayerInLobby(player)) || (isPlayerReadyToPlay(player)));
    }

    public void addPlayerToLobby(Player player)                 // None -> Lobby
    {
        lobby.add(player);
    }

    public void movePlayerToReadyToPlay(Player player)          // Lobby -> Ready
    {
        lobby.remove(player);
        if (!isPlayerReadyToPlay(player))
        {
            readyToPlay.add(player);
        }
    }

    public void createNewTableFromReadyToPlay(int playerNumber) throws Exception // Ready -> Table
    {
        ArrayList<Player> transferPlayers = new ArrayList<Player>(readyToPlay.subList(0, playerNumber));
        for (int i = 0; (i < playerNumber) && (!readyToPlay.isEmpty()); i++)
        {
            readyToPlay.remove(0);
        }

        addTable(transferPlayers);
    }

    public void removePlayerToLobby(Player player)              // (Ready || Table) -> Lobby
    {
        if (readyToPlay.contains(player))
        {
            readyToPlay.remove(player);
            lobby.add(player);
            return;
        }

        for (Table table : tables)
        {
            if (table.isEmpty())
            {
                removeTable(table);
            }
            if (table.getPlayers().contains(player))
            {
                table.removePlayer(player);
                if (table.isEmpty())
                {
                    removeTable(table);
                }
                lobby.add(player);
                return;
            }
        }
    }

    public void removePlayer(Player player)                     // Any -> None
    {
        if (lobby.contains(player))
        {
            lobby.remove(player);

        } else if (readyToPlay.contains(player))
        {
            readyToPlay.remove(player);

        } else
        {
            for (Table table : tables)
            {
                if (table.isEmpty())
                {
                    removeTable(table);
                }
                if (table.getPlayers().contains(player))
                {
                    table.removePlayer(player);
                    if (table.isEmpty())
                    {
                        removeTable(table);
                    }
                    return;
                }
            }
        }
    }
}
