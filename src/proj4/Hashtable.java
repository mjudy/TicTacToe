package proj4;

/**
 * @author theghv
 * @version 1.0 Date: 4/23/14 Time: 8:54 PM
 */
public class Hashtable<K, V>
{
    static final int DEFAULT_SIZE = 101;
    private int entries;
    private int collisions;
    private Node<K, V>[] table;

    public Hashtable()
    {
        this(DEFAULT_SIZE);
    }

    public Hashtable(int size)
    {
        entries = 0;
        collisions = 0;
        table = new Node[size];
    }

    public boolean containsKey(K key)
    {

        for(int i = 0; i < table.length; i++)
        {
            if(table[i] == key)
            {
                return true;
            }
        }
        return false;
    }

    public V get(K key)
    {
        if(table[getPositionInHashtable(key)] != null)
        {
            return table[getPositionInHashtable(key)].data;
        }
        else
            return null;
    }

    public int numCollisions()
    {
        return collisions;
    }

    public int numEntries()
    {
        return entries;
    }

    public int numSlots()
    {
        return table.length;
    }

    public void put(K key, V data)
    {
        int position = getPositionInHashtable(key);
        if(containsKey(key))
        {
            table[position].data = data;
        }
        else
        {
            table[position] = new Node<K, V> (key, data);
            entries++;
        }
    }

    private int trueHash(K key)
    {
        int hash = key.hashCode();
        hash %= table.length;

//        System.out.println(hash);

        if (hash < 0)
        {
            hash += table.length;
        }
        return hash;
    }
    
    private int getPositionInHashtable(K key)
    {
        int offset = 1;
        int position = trueHash(key);

        while (table[position] != null && table[position].data.equals(key))
        {
            position += offset;
            offset += 2;
            collisions++;
            if(position >= table.length)
            {
                position -= table.length;
            }
        }

        return position;
    }

    private class Node<K, V>
    {
        private K key;
        private V data;

        Node (K k, V v)
        {
            key = k;
            data = v;
        }
    }
}
