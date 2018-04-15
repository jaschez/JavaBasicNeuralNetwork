package redneuronal;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Perceptron {
    public float[] inputs;
    LinkedList<LinkedList<Neuron>> red;
    
    public Perceptron(int[] neuronsPerLayer, float[] inputs){
       this.inputs = inputs; 
        
       red = new LinkedList<LinkedList<Neuron>>();
       initNet(neuronsPerLayer);
    }
    
    public Perceptron(LinkedList<LinkedList<Neuron>> red){
       this.red = red;
    }
    
    private void initNet(int[] neuronsPerLayer){
        for(int i = 0; i < neuronsPerLayer.length;i++){
            LinkedList<Neuron> layer  = new LinkedList<Neuron>();
            int neurons = neuronsPerLayer[i];
            
            for(int z = 0; z < neurons;z++){
                if(i == 0){
                    layer.add(new Neuron(inputs));
                }else{
                    layer.add(new Neuron(new float[red.get(i-1).size()]));
                }
            }
            red.add(layer);
        }
    }
    
    public void UpdateInputs(float[] inputs){
        this.inputs = inputs;
    }
    
    public float[] Calculate(){
        float[] outputs = new float[red.get(red.size()-1).size()];
        
        for(int i = 0; i < red.size();i++){
            LinkedList<Neuron> layer = red.get(i);
            float[] neuronsOutput;
            
            if(i != 0){
                //Obtener calculos como entrada de las siguientes neuronas
                neuronsOutput = new float[red.get(i-1).size()];
                for (int neurona = 0; neurona < neuronsOutput.length;neurona++){
                    neuronsOutput[neurona] = red.get(i-1).get(neurona).output;
                }
            }else{
                neuronsOutput = new float[0];
            }
            
            for(int z = 0;z < layer.size();z++){
                
                if(i==0){
                    //Asignando a cada neurona el numero de entradas a recibir (DE LOS VALORES DE ENTRADA DEL PERCEPTRON)
                    layer.get(z).inputs = this.inputs;
                }else{
                    //Asignando a cada neurona el numero de entradas a recibir (DE LA ANTERIOR CAPA DE NEURONAS DEL PERCEPTRON)
                    layer.get(z).inputs = neuronsOutput;
                }
                
                layer.get(z).Calculation();
                
                if(i == red.size()-1){
                    outputs[z] = layer.get(z).output;
                }
            }
        }
        
        return outputs;
    }
}

class Neuron{
    public float[] inputs, weights;
    public float bias;
    public float output;
    
    Random rand;
    
    public Neuron(){
        
    }
    
    public Neuron(float[] inputs){
        this.inputs = inputs;
        this.weights = new float[inputs.length];
        
        rand = new Random();
        
        for(int i = 0; i < weights.length; i++){
            weights[i] = rand.nextFloat()*2-1;
        }
        
        bias = rand.nextFloat()*2-1;
    }
    
    public Neuron(float[] inputs, float[] weights,float bias){
        this.inputs = inputs;
        this.weights = weights;
        this.bias = bias;
    }
    
    public float Calculation(){
        float result = 0;
        
        for(int i = 0; i < inputs.length;i++){
            result += inputs[i]*weights[i];
        }
        
        float output = activation(result+bias);       
        this.output = output;

        return output;
    }
    
    float activation(float x){
        //return(x>=0?1:-1);
        return(1/(1+(float)(Math.pow(Math.E,-x))));
    }
}
