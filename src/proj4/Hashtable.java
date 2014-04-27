package proj4;

/**
 * @author theghv
 * @version 1.0 Date: 4/23/14 Time: 8:54 PM
 */
public class Hashtable<K, V>
{
    static final int DEFAULT_SIZE = 267;
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
        if(table[getPosition(key)] != null)
        {
            return table[getPosition(key)].data;
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
        else if(table[position] == null)
        {
            table[position] = new Node<K, V> (key, data);
            entries++;
        }
    }

    private int trueHash(K key)
    {
        int hash = key.hashCode();
        hash %= table.length;

        if (hash < 0)
        {
            hash += table.length - 1;
        }
//        System.out.println(hash);
        return hash;
    }

    private int getPosition(K key)
    {
        int offset = 1;
        int position = trueHash(key);

        while (table[position] != null && table[position].key.equals(key))
        {
            position += offset;
            offset += 2;
            if(position < 0)
            {
                position -= position;
                position += offset;
            }
            if(position >= table.length)
            {
                position -= table.length;
            }
        }

        return position;
    }

    private int getPositionInHashtable(K key)
    {
        int offset = 1;
        int position = trueHash(key);

        while (table[position] != null && table[position].key.equals(key))
        {
            position += offset;
            offset += 2;
            collisions++;
            if(position < 0)
            {
                position -= position;
                position += offset;
            }
            if(position >= table.length)
            {
                position -= table.length;
            }
        }

        return position;
    }

//    public String toString()
//    {
//        int count = 0;
//        String str = "";
//        for(Node i : table)
//        {
//            if(i.key != null)
//            {
//                str += "[" + count + "] " + i.key.toString() + "\n";
//            }
//            count++;
//        }
//        return str;
//    }

    private class Node<K, V>
    {
        private K key;
        private V data;

        Node (K k, V v)
        {
            key = k;
            data = v;
        }

        public String toString()
        {
            return key.toString();
        }
    }
}
